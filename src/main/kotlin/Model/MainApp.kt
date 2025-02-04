package Model// src/main/kotlin/MainApp.kt

import Controller.StudentListController
import View.StudentListView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.stage.Stage

class MainApp : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Студенты"

        val tabPane = TabPane()
        tabPane.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

        val studentListTab = Tab("Список студентов")
        val secondTab = Tab("Второй раздел")
        val thirdTab = Tab("Третий раздел")
        System.out.println("привет мир")

        tabPane.tabs.addAll(studentListTab, secondTab, thirdTab)
        studentListTab.content = StudentListView()
        studentListTab.setOnSelectionChanged {
            if (studentListTab.isSelected) {
                val studentListView = StudentListView()
                StudentListController(studentListView)
                studentListTab.content = studentListView
            }
        }

        val scene = Scene(tabPane, 800.0, 600.0)
        scene.stylesheets.add("styles.css")
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}