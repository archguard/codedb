package org.archguard.codedb.clui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.Key.Companion.Minus
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CodeDB",
        state = rememberWindowState(width = 800.dp, height = 480.dp)
    ) {
        MaterialTheme {
            var consumedText by remember { mutableStateOf(0) }
            var text by remember { mutableStateOf("") }
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                TextField(
                    placeholder = { Text("Run a command") },
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.onPreviewKeyEvent {
                        when {
                            (it.isCtrlPressed && it.key == Minus && it.type == KeyEventType.KeyUp) -> {
                                consumedText -= text.length
                                text = ""
                                true
                            }
                            (it.isCtrlPressed && it.key == Key.Equals && it.type == KeyEventType.KeyUp) -> {
                                consumedText += text.length
                                text = ""
                                true
                            }
                            else -> false
                        }
                    }
                )

                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        // todo: run the command
                    }) {
                    Text("Reset")
                }
            }
        }
    }
}
