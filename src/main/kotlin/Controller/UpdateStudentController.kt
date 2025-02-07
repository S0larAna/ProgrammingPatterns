package Controller

import DBConnection.DatabaseManager
import Model.Student
import Model.StudentList
import DBConnection.Students_list_DB
import Model.main
import javafx.stage.Stage
import java.sql.Connection

class UpdateStudentController(private val student: Student, private val students: StudentList, private val mainController: StudentListController) {
    fun updateStudent(firstNameField: String, lastNameField: String, middleNameField: String) {
        val updatedStudent = Student(
            student.id,
            lastNameField,
            firstNameField,
            middleNameField,
            student.phone,
            student.telegram,
            student.email,
            student.github
        )
        /*val studentDb = Students_list_DB(dbConnection)
        studentDb.updateStudent(student.id, updatedStudent)*/
        students.replaceStudent(student.id, updatedStudent)
        students.writeToFile(mutableListOf(updatedStudent))
        System.out.println("Updated student: $updatedStudent")
        mainController.updateTableData()
    }
}