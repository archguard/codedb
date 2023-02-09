package org.archguard.core.client

// todo: align to ArchGuard CLI config
class CodedbConfig {
    /**
     * The URL of the server.
     */
    val serverUrl: String = "http://localhost:8080"

    /**
     * The path to the project root.
     */
    val projectRoot: String = "."

    companion object {
        fun fromYaml(yaml: String): CodedbConfig {
            // todo: serialize from yaml
            return CodedbConfig()
        }
    }
}

