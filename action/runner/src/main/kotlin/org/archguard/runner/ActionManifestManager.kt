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

        while (parser.nextToken() != null) {
            when (parser.currentName) {
                "name" -> definition.name = parser.valueAsString
                "description" -> definition.description = parser.valueAsString
                "version" -> definition.version = parser.valueAsString
                "author" -> definition.author = parser.valueAsString
                "runs" -> {

                }
            }

            parser.nextToken()
        }

        return definition
    }
}