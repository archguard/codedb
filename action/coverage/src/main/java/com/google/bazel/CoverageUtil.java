package com.google.bazel;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.bazel.Constants.*;
import static java.lang.Math.max;
import static java.nio.charset.StandardCharsets.UTF_8;

public class CoverageUtil {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    /**
     * Returns a set of source file names from the given manifest.
     *
     * <p>The manifest contains file names line by line. Each file can either be a source file (e.g.
     * .java, .cc) or a coverage metadata file (e.g. .gcno, .em).
     *
     * <p>This method only returns the C++ source files, ignoring the other files as they are not
     * necessary when putting together the final coverage report.
     */
    static Set<String> getSourcesFromSourceFileManifest(String sourceFileManifest) {
      Set<String> sourceFiles = new HashSet<>();
      try (FileInputStream inputStream = new FileInputStream(new File(sourceFileManifest));
           InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
           BufferedReader reader = new BufferedReader(inputStreamReader)) {
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
          if (!isMetadataFile(line)) {
            sourceFiles.add(line);
          }
        }
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Error reading file " + sourceFileManifest + ": " + e.getMessage());
      }
      return sourceFiles;
    }

    static boolean isMetadataFile(String filename) {
      return filename.endsWith(".gcno") || filename.endsWith(".em");
    }

    public static List<File> getGcovInfoFiles(List<File> filesInCoverageDir) {
      List<File> gcovFiles = getFilesWithExtension(filesInCoverageDir, GCOV_EXTENSION);
      if (gcovFiles.isEmpty()) {
        logger.log(Level.INFO, "No gcov info file found.");
      } else {
        logger.log(Level.INFO, "Found " + gcovFiles.size() + " gcov info files.");
      }
      return gcovFiles;
    }

    public static List<File> getGcovJsonInfoFiles(List<File> filesInCoverageDir) {
      List<File> gcovJsonFiles = getFilesWithExtension(filesInCoverageDir, GCOV_JSON_EXTENSION);
      if (gcovJsonFiles.isEmpty()) {
        logger.log(Level.INFO, "No gcov json file found.");
      } else {
        logger.log(Level.INFO, "Found " + gcovJsonFiles.size() + " gcov json files.");
      }
      return gcovJsonFiles;
    }

    /**
     * Returns a .profdata file from the given files or null if none or more profdata files were
     * found.
     */
    static File getProfdataFileOrNull(List<File> files) {
      List<File> profdataFiles = getFilesWithExtension(files, PROFDATA_EXTENSION);
      if (profdataFiles.isEmpty()) {
        logger.log(Level.INFO, "No .profdata file found.");
        return null;
      }
      if (profdataFiles.size() > 1) {
        logger.log(
            Level.SEVERE,
            "Bazel currently supports only one profdata file per test. "
                + profdataFiles.size()
                + " .profadata files were found instead.");
        return null;
      }
      logger.log(Level.INFO, "Found one .profdata file.");
      return profdataFiles.get(0);
    }

    public static List<File> getTracefiles(LcovMergerFlags flags, List<File> filesInCoverageDir) {
      List<File> lcovTracefiles = new ArrayList<>();
      if (flags.reportsFile() != null) {
        lcovTracefiles = getTracefilesFromFile(flags.reportsFile());
      } else if (flags.coverageDir() != null) {
        lcovTracefiles = getFilesWithExtension(filesInCoverageDir, TRACEFILE_EXTENSION);
      }
      if (lcovTracefiles.isEmpty()) {
        logger.log(Level.INFO, "No lcov file found.");
      } else {
        logger.log(Level.INFO, "Found " + lcovTracefiles.size() + " tracefiles.");
      }
      return lcovTracefiles;
    }

    /**
     * Reads the content of the given file and returns a matching map.
     *
     * <p>It assumes the file contains lines in the form key:value. For each line it creates an entry
     * in the map with the corresponding key and value.
     */
    static ImmutableMap<String, String> getMapFromFile(String file) {
      ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();

      try (FileInputStream inputStream = new FileInputStream(file);
          InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
          BufferedReader reader = new BufferedReader(inputStreamReader)) {
        for (String keyToValueLine = reader.readLine();
            keyToValueLine != null;
            keyToValueLine = reader.readLine()) {
          String[] keyAndValue = keyToValueLine.split(":");
          if (keyAndValue.length == 2) {
            mapBuilder.put(keyAndValue[0], keyAndValue[1]);
          }
        }
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Error reading file " + file + ": " + e.getMessage());
      }
      return mapBuilder.buildOrThrow();
    }

    public static Coverage parseFiles(List<File> files, Parser parser, int parallelism)
        throws ExecutionException, InterruptedException {
      if (parallelism == 1) {
        return parseFilesSequentially(files, parser);
      } else {
        return parseFilesInParallel(files, parser, parallelism);
      }
    }

    static Coverage parseFilesSequentially(List<File> files, Parser parser) {
      Coverage coverage = new Coverage();
      for (File file : files) {
        try {
          logger.log(Level.INFO, "Parsing file " + file);
          List<SourceFileCoverage> sourceFilesCoverage = parser.parse(new FileInputStream(file));
          for (SourceFileCoverage sourceFileCoverage : sourceFilesCoverage) {
            coverage.add(sourceFileCoverage);
          }
        } catch (IOException e) {
          logger.log(
              Level.SEVERE,
              "File " + file.getAbsolutePath() + " could not be parsed due to: " + e.getMessage(),
              e);
          System.exit(1);
        }
      }
      return coverage;
    }

    static Coverage parseFilesInParallel(List<File> files, Parser parser, int parallelism)
        throws ExecutionException, InterruptedException {
      ForkJoinPool pool = new ForkJoinPool(parallelism);
      int partitionSize = max(1, files.size() / parallelism);
      List<List<File>> partitions = Lists.partition(files, partitionSize);
      return pool.submit(
              () ->
                  partitions.parallelStream()
                      .map((p) -> parseFilesSequentially(p, parser))
                      .reduce((c1, c2) -> Coverage.merge(c1, c2))
                      .orElse(Coverage.create()))
          .get();
    }

    /**
     * Returns a list of all the files with the given extension found recursively under the given dir.
     */
    @VisibleForTesting
    public static List<File> getCoverageFilesInDir(String dir) {
      List<File> files = new ArrayList<>();
      try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
        files =
            stream
                .filter(
                    p ->
                        p.toString().endsWith(TRACEFILE_EXTENSION)
                            || p.toString().endsWith(GCOV_EXTENSION)
                            || p.toString().endsWith(GCOV_JSON_EXTENSION)
                            || p.toString().endsWith(PROFDATA_EXTENSION))
                .map(path -> path.toFile())
                .collect(Collectors.toList());
      } catch (IOException ex) {
        logger.log(Level.SEVERE, "Error reading folder " + dir + ": " + ex.getMessage());
      }
      return files;
    }

    static List<File> getFilesWithExtension(List<File> files, String extension) {
      return files.stream()
          .filter(file -> file.toString().endsWith(extension))
          .collect(Collectors.toList());
    }

    static List<File> getTracefilesFromFile(String reportsFile) {
      List<File> datFiles = new ArrayList<>();
      try (FileInputStream inputStream = new FileInputStream(reportsFile)) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        for (String tracefile = reader.readLine(); tracefile != null; tracefile = reader.readLine()) {
          // TODO(elenairina): baseline coverage contains some file names that need to be modified
          if (!tracefile.endsWith("baseline_coverage.dat")) {
            datFiles.add(new File(tracefile));
          }
        }

      } catch (IOException e) {
        logger.log(Level.SEVERE, "Error reading file " + reportsFile + ": " + e.getMessage());
      }
      return datFiles;
    }
}
