// Copyright 2016 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.bazel;

import static java.lang.Math.max;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Command line utility to convert raw coverage files to lcov (text) format. */
public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String... args) {
    try {
      int exitCode = runWithArgs(args);
      System.exit(exitCode);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Unhandled exception on lcov tool: " + e.getMessage(), e);
      System.exit(1);
    }
  }

  static int runWithArgs(String... args) throws ExecutionException, InterruptedException {
    LcovMergerFlags flags = null;
    try {
      flags = LcovMergerFlags.parseFlags(args);
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, e.getMessage());
      return 1;
    }

    File outputFile = new File(flags.outputFile());

    List<File> filesInCoverageDir =
        flags.coverageDir() != null
            ? CoverageUtil.getCoverageFilesInDir(flags.coverageDir())
            : ImmutableList.of();
    Coverage coverage =
        Coverage.merge(
            CoverageUtil.parseFiles(
                CoverageUtil.getTracefiles(flags, filesInCoverageDir),
                LcovParser::parse,
                flags.parseParallelism()),
            CoverageUtil.parseFiles(
                CoverageUtil.getGcovInfoFiles(filesInCoverageDir), GcovParser::parse, flags.parseParallelism()),
            CoverageUtil.parseFiles(
                CoverageUtil.getGcovJsonInfoFiles(filesInCoverageDir),
                GcovJsonParser::parse,
                flags.parseParallelism()));

    if (flags.sourcesToReplaceFile() != null) {
      coverage.maybeReplaceSourceFileNames(CoverageUtil.getMapFromFile(flags.sourcesToReplaceFile()));
    }

    File profdataFile = CoverageUtil.getProfdataFileOrNull(filesInCoverageDir);
    if (coverage.isEmpty()) {
      int exitStatus = 0;
      if (profdataFile == null) {
        try {
          logger.log(Level.WARNING, "There was no coverage found.");
          if (!Files.exists(outputFile.toPath())) {
            Files.createFile(outputFile.toPath()); // Generate empty declared output
          }
          exitStatus = 0;
        } catch (IOException e) {
          logger.log(
              Level.SEVERE,
              "Could not create empty output file "
                  + outputFile.getName()
                  + " due to: "
                  + e.getMessage());
          exitStatus = 1;
        }
      } else {
        // Bazel doesn't support yet converting profdata files to lcov. We still want to output a
        // coverage report so we copy the content of the profdata file to the output file. This is
        // not ideal but it unblocks some Bazel C++
        // coverage users.
        // TODO(#5881): Add support for profdata files.
        try {
          Files.copy(profdataFile.toPath(), outputFile.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
          logger.log(
              Level.SEVERE,
              "Could not copy file "
                  + profdataFile.getName()
                  + " to output file "
                  + outputFile.getName()
                  + " due to: "
                  + e.getMessage());
          exitStatus = 1;
        }
      }
      return exitStatus;
    }

    if (!coverage.isEmpty() && profdataFile != null) {
      // If there is one profdata file then there can't be other types of reports because there is
      // no way to merge them.
      // TODO(#5881): Add support for profdata files.
      logger.log(
          Level.WARNING,
          "Bazel doesn't support LLVM profdata coverage amongst other coverage formats.");
      return 0;
    }

    if (!flags.filterSources().isEmpty()) {
      coverage = Coverage.filterOutMatchingSources(coverage, flags.filterSources());
    }

    if (flags.hasSourceFileManifest()) {
      coverage =
          Coverage.getOnlyTheseSources(
              coverage, CoverageUtil.getSourcesFromSourceFileManifest(flags.sourceFileManifest()));
    }

    if (coverage.isEmpty()) {
      try {
        logger.log(Level.WARNING, "There was no coverage found.");
        if (!Files.exists(outputFile.toPath())) {
          Files.createFile(outputFile.toPath()); // Generate empty declared output
        }
        return 0;
      } catch (IOException e) {
        logger.log(
            Level.SEVERE,
            "Could not create empty output file "
                + outputFile.getName()
                + " due to: "
                + e.getMessage());
        return 1;
      }
    }

    int exitStatus = 0;

    try {
      LcovPrinter.print(new FileOutputStream(outputFile), coverage);
    } catch (IOException e) {
      logger.log(
          Level.SEVERE,
          "Could not write to output file " + outputFile + " due to " + e.getMessage());
      exitStatus = 1;
    }
    return exitStatus;
  }

}
