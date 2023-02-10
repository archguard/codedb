package org.archguard.runner.pipeline

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar
import kotlinx.serialization.decodeFromString

fun YamlNode.stringify(): String {
    return this.contentToString().withoutQuotes()
}

private fun String.withoutQuotes(): String {
    if (this.startsWith("\"") && this.endsWith("\"")) {
        return this.substring(1, this.length - 1)
    }

    if (this.startsWith("'") && this.endsWith("'")) {
        return this.substring(1, this.length - 1)
    }

    return this
}

inline fun <reified T> YamlNode.objectValue(name: String): T {
    val node = this.yamlMap.get<YamlNode>(name)?.contentToString()
    return Yaml.default.decodeFromString(node ?: "")
}

fun YamlNode.flatString(name: String): String {
    return this.yamlMap.get<YamlNode>(name)?.yamlScalar?.content ?: ""
}