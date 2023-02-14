package org.archguard.runner.handler

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep

class HandlerFactory {
    companion object {
        fun create(data: ActionStep, context: RunnerContext): Handler {
            if (data.run.isEmpty()) {
                return CompositeActionHandler(data, context, data.uses)
            }

            return when (data.run.scriptType()) {
                ScriptType.KotlinScriptFile -> KotlinScriptActionHandler(data, context, data.name)
                ScriptType.Shell -> CommandActionHandler(data, context, data.name)
                ScriptType.ShellScriptFile -> ShellScriptActionHandler(data, context, data.name)
            }
        }
    }
}

private fun String.scriptType(): ScriptType {
    if (this.endsWith(".kts")) {
        return ScriptType.KotlinScriptFile
    }
    if (this.endsWith(".sh")) {
        return ScriptType.ShellScriptFile
    }

    return ScriptType.Shell
}

sealed class ScriptType {
    object KotlinScriptFile : ScriptType()

    object ShellScriptFile : ScriptType()

    object Shell : ScriptType()
}