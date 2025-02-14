package Controller

import Model.Student
import Model.StudentList

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
        students.replaceStudent(student.id, updatedStudent)
        System.out.println("Updated student: $updatedStudent")
        mainController.updateTableData()
    }
}