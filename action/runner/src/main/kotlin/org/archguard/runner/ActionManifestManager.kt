package org.archguard.runner

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
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
                    convertObject(parser, definition, ::convertConfig)
                }
                "jobs" -> {
                    convertObject(parser, definition, ::convertJobs)
                }
            }

            token = parser.nextToken()
        }

        return definition
    }

    private fun convertJobs(parser: JsonParser, actionDefinitionData: ActionDefinitionData) {
        when(parser.currentName) {

        }
    }

    private fun convertObject(
        parser: JsonParser,
        definition: ActionDefinitionData,
        function: (JsonParser, ActionDefinitionData) -> Unit
    ) {
        // START_OBJECT
        parser.nextToken()
        // FIELD_NAME
        parser.nextToken()

        function(parser, definition)

        // END_OBJECT
        parser.nextToken()
    }

    private fun convertConfig(
        parser: JsonParser,
        definition: ActionDefinitionData
    ) {
        when (parser.currentName) {
            "metric" -> definition.config.metric = parser.valueAsBoolean
            "server" -> {
                convertObject(parser, definition, ::convertServerConfig)
            }
        }
    }

    private fun convertServerConfig(parser: JsonParser, definition: ActionDefinitionData) {
        when (parser.currentName) {
            "url" -> {
                parser.nextToken()
                definition.config.server.url = parser.valueAsString
            }
        }
    }
}
