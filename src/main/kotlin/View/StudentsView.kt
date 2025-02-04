import View.StudentListView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.stage.Stage

class StudentView : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Student data manager"

        val tabPane = TabPane()
        tabPane.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

        val studentListTab = Tab("Список студентов")
        val secondTab = Tab("Второй раздел")
        val thirdTab = Tab("Третий раздел")

        tabPane.tabs.addAll(studentListTab, secondTab, thirdTab)

        val scene = Scene(tabPane, 800.0, 600.0)
        scene.stylesheets.add("styles.css")
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(StudentView::class.java)
}
