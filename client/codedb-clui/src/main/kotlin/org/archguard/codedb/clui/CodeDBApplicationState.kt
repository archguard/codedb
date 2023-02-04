package org.archguard.codedb.clui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import org.archguard.codedb.clui.common.Settings
import org.archguard.codedb.clui.window.CodeDBWindowState

@Composable
fun rememberApplicationState() = remember {
    CodeDBApplicationState().apply {
        newWindow()
    }
}

class CodeDBApplicationState {
    val settings = Settings()
    val tray = TrayState()

    private val _windows = mutableStateListOf<CodeDBWindowState>()
    val windows: List<CodeDBWindowState> get() = _windows

    fun newWindow() {
        _windows.add(
            CodeDBWindowState(
                application = this,
                path = null,
                exit = _windows::remove
            )
        )
    }

    fun sendNotification(notification: Notification) {
        tray.sendNotification(notification)
    }

    suspend fun exit() {
        val windowsCopy = windows.reversed()
        for (window in windowsCopy) {
            if (!window.exit()) {
                break
            }
        }
    }
}