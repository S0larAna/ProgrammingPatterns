import App.StudentListView
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.scene.layout.Priority

class StudentView : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Управление студентами"

        val tabPane = TabPane()
        tabPane.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

        val studentListTab = Tab("Список студентов")
        val secondTab = Tab("Второй раздел")
        val thirdTab = Tab("Третий раздел")
        System.out.println("Привет мир!")

        tabPane.tabs.addAll(studentListTab, secondTab, thirdTab)
        studentListTab.content = StudentListView()

        val scene = Scene(tabPane, 800.0, 600.0)
        scene.stylesheets.add("styles.css")
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(StudentView::class.java)
}
