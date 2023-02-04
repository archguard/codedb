package org.archguard.codedb.clui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import org.archguard.codedb.clui.common.LocalAppResources
import org.archguard.codedb.clui.common.rememberAppResources

fun main() = application {
    CompositionLocalProvider(LocalAppResources provides rememberAppResources()) {
        CodeDBApplication(rememberApplicationState())
    }
}