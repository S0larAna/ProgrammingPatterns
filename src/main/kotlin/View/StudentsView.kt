import Controller.StudentAppController
import View.StudentListView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.stage.Stage

class StudentView() : Application() {
    private val studentAppController = StudentAppController(this)
    val studentListTab = Tab("Список студентов")
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Студенты"
        val tabPane = TabPane()
        tabPane.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        System.out.println("привет мир")

        tabPane.tabs.addAll(studentListTab)
        studentListTab.content = StudentListView()

        studentListTab.setOnSelectionChanged {
            studentAppController.updateTabContent()
        }

        val scene = Scene(tabPane, 800.0, 600.0)
        scene.stylesheets.add("styles.css")
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(StudentView::class.java)
}
