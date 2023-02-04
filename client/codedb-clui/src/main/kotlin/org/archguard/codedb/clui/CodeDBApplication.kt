package org.archguard.codedb.clui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Tray
import org.archguard.codedb.clui.common.LocalAppResources
import kotlinx.coroutines.launch
import org.archguard.codedb.clui.window.CodeDBWindow

@Composable
fun ApplicationScope.CodeDBApplication(state: CodeDBApplicationState) {
    if (state.settings.isTrayEnabled && state.windows.isNotEmpty()) {
        ApplicationTray(state)
    }

    for (window in state.windows) {
        key(window) {
            CodeDBWindow(window)
        }
    }
}

@Composable
private fun ApplicationScope.ApplicationTray(state: CodeDBApplicationState) {
    Tray(
        LocalAppResources.current.icon,
        state = state.tray,
        tooltip = "CodeDB",
        menu = { ApplicationMenu(state) }
    )
}

@Composable
private fun MenuScope.ApplicationMenu(state: CodeDBApplicationState) {
    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }

    Item("New", onClick = state::newWindow)
    Separator()
    Item("Exit", onClick = { exit() })
}