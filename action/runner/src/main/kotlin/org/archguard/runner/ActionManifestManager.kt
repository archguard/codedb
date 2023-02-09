package org.archguard.runner

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.runner.pipeline.ActionConfig
import org.archguard.runner.pipeline.ActionDefinitionData


/**
 * parse action manifest or json schema
 */
class ActionManifestManager {
    fun load(manifest: String): ActionDefinitionData {
        val definition = ActionDefinitionData()

        val factory = YAMLFactory()
        val parser: JsonParser = factory.createParser(manifest)
        var token = parser.nextToken()

        while (token != null) {
            when (parser.currentName) {
                "name" -> definition.name = parser.valueAsString
                "description" -> definition.description = parser.valueAsString
                "version" -> definition.version = parser.valueAsString
                "author" -> definition.author = parser.valueAsString
                "config" -> {
                    // START_OBJECT
                    parser.nextToken()
                    // FIELD_NAME
                    parser.nextToken()

                    when(parser.currentName) {
                        "metric" -> definition.config.metric = parser.valueAsBoolean
                        "server" -> {
                            // START_OBJECT
                            parser.nextToken()
                            // FIELD_NAME
                            parser.nextToken()

                            when(parser.currentName) {
                                "url" -> {
                                    parser.nextToken()
                                    definition.config.server.url = parser.valueAsString
                                }
                            }

                            // END_OBJECT
                            parser.nextToken()
                        }
                    }

                    // END_OBJECT
                    parser.nextToken()
                }
                "runs" -> {

                }
            }

            token = parser.nextToken()
        }

        return definition
    }
}
