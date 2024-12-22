import App.MainApp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

class MainWindow {
    companion object {
        fun createAndShow() {
            application {
                    Window(
                        onCloseRequest = ::exitApplication,
                        state = rememberWindowState(
                            width = 1250.dp,
                            height = 800.dp
                        ),
                        title = "Students"
                    )
                 {
                    MainApp().App()
                }
            }
        }
    }
}