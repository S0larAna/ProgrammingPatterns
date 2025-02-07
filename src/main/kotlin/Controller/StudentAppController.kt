package Controller

import StudentView
import View.StudentListView
import javafx.scene.control.Tab

class StudentAppController(private val studentView: StudentView) {
    fun updateTabContent() {
        if (studentView.studentListTab.isSelected) {
            val studentListView = StudentListView()
            StudentListController(studentListView)
            studentView.studentListTab.content = studentListView
        }

    }

}