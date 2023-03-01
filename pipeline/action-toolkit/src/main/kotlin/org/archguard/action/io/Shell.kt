import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermission
import java.util.*

val IS_WINDOWS = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("win")

/**
 * On OSX/Linux, true if path starts with '/'. On Windows, true for paths like:
 * \, \hello, \\hello\share, C:, and C:\hello (and corresponding alternate separator cases).
 */
fun isRooted(p: String): Boolean {
    if (p.isEmpty()) {
        throw Exception("isRooted() parameter \"p\" cannot be empty")
    }
    return if (IS_WINDOWS) {
        p.startsWith("\\") || Regex("^[A-Z]:", RegexOption.IGNORE_CASE).containsMatchIn(p)
    } else {
        p.startsWith("/")
    }
}



fun tryGetExecutablePath(filePath: File, extensions: List<String>): String? {
    var stats: File? = null
    try {
        // test file exists
        stats = filePath
    } catch (err: Exception) {
        if (err.message != "ENOENT") {
            // eslint-disable-next-line no-console
            println("Unexpected error attempting to determine if executable file exists '${filePath}': ${err}")
        }
    }
    if (stats != null && stats.isFile) {
        if (IS_WINDOWS) {
            // on Windows, test for valid extension
            val upperExt = filePath.extension.toUpperCase()
            if (extensions.any { validExt -> validExt.toUpperCase() == upperExt }) {
                return filePath.absolutePath
            }
        } else {
            if (isUnixExecutable(stats)) {
                return filePath.absolutePath
            }
        }
    }

    // try each extension
    val originalFilePath = filePath
    for (extension in extensions) {
        var filePath = File(originalFilePath.absolutePath + extension)

        stats = null
        try {
            stats = filePath
        } catch (err: Exception) {
            if (err.message != "ENOENT") {
                // eslint-disable-next-line no-console
                println("Unexpected error attempting to determine if executable file exists '${filePath}': ${err}")
            }
        }

        if (stats != null && stats.isFile) {
            if (IS_WINDOWS) {
                // preserve the case of the actual file (since an extension was appended)
                try {
                    val directory = filePath.parentFile
                    val upperName = filePath.name.toUpperCase()
                    for (actualName in directory.list()) {
                        if (upperName == actualName.toUpperCase()) {
                            filePath = File(directory, actualName)
                            break
                        }
                    }
                } catch (err: Exception) {
                    // eslint-disable-next-line no-console
                    println("Unexpected error attempting to determine the actual case of the file '${filePath}': ${err}")
                }

                return filePath.absolutePath
            } else {
                if (isUnixExecutable(stats)) {
                    return filePath.absolutePath
                }
            }
        }
    }

    return null
}

fun isUnixExecutable(file: File): Boolean {
    val permissions = Files.getPosixFilePermissions(file.toPath())
    return permissions.contains(PosixFilePermission.OWNER_EXECUTE) ||
            permissions.contains(PosixFilePermission.GROUP_EXECUTE) ||
            permissions.contains(PosixFilePermission.OTHERS_EXECUTE)
}

object Shell {
    fun which(tool: String, check: Boolean = false): String {
        if (tool.isEmpty()) {
            throw Error("parameter 'tool' is required")
        }
        if (check) {
            val result = which(tool, false)
            if (result.isEmpty()) {
                if (IS_WINDOWS) {
                    throw Error("Unable to locate executable file: $tool. Please verify either the file path exists or the file can be found within a directory specified by the PATH environment variable. Also verify the file has a valid extension for an executable file.")
                } else {
                    throw Error("Unable to locate executable file: $tool. Please verify either the file path exists or the file can be found within a directory specified by the PATH environment variable. Also check the file mode to verify the file is executable.")
                }
            }
            return result
        }
        val matches = findInPath(tool)
        if (matches.isNotEmpty()) {
            return matches[0]
        }
        return ""
    }

    /**
     * Returns a list of all occurrences of the given tool on the system path.
     *
     * @param tool     the name of the tool
     * @returns        a list of paths to the given tool
     */
    fun findInPath(tool: String): List<String> {
        if (tool.isEmpty()) {
            throw Error("parameter 'tool' is required")
        }
        val extensions = mutableListOf<String>()
        if (IS_WINDOWS && System.getenv("PATHEXT") != null) {
            for (extension in System.getenv("PATHEXT").split(File.pathSeparatorChar)) {
                if (extension.isNotEmpty()) {
                    extensions.add(extension)
                }
            }
        }
        if (isRooted(tool)) {
            val filePath = tryGetExecutablePath(File(tool), extensions)
            if (filePath != null) {
                return listOf(filePath)
            }
            return emptyList()
        }
        if (tool.contains(File.separator)) {
            return emptyList()
        }
        val directories = mutableListOf<String>()
        if (System.getenv("PATH") != null) {
            for (p in System.getenv("PATH").split(File.pathSeparatorChar)) {
                if (p.isNotEmpty()) {
                    directories.add(p)
                }
            }
        }
        val matches = mutableListOf<String>()
        for (directory in directories) {
            val filePath = tryGetExecutablePath(File(directory, tool), extensions)
            if (filePath != null) {
                matches.add(filePath)
            }
        }
        return matches
    }

}