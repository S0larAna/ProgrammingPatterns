package Controller

import AddStudentWindow
import Model.Data_list_student_short
import Model.Student
import Model.StudentBase
import Model.StudentList
import javafx.scene.control.TextField

class AddStudentController(private val addStudentView: AddStudentWindow, private val studentList: StudentList, private val studentListController: StudentListController) {
    fun validateFields() {
        val fields = listOf(
            addStudentView.lastNameField to StudentBase::isValidName,
            addStudentView.firstNameField to StudentBase::isValidName,
            addStudentView.middleNameField to StudentBase::isValidName,
            addStudentView.phoneField to StudentBase::isValidPhoneNumber,
            addStudentView.telegramField to StudentBase::isValidTelegramHandle,
            addStudentView.emailField to StudentBase::isValidEmail,
            addStudentView.githubField to StudentBase::isValidGithubUsername
        )

        var allValid = true
        for ((field, validator) in fields) {
            val isValid = validator(field.text)
            addStudentView.updateValidationState(isValid, field)
            if (!isValid) allValid = false
        }
        addStudentView.updateOkButtonState(allValid)
    }

    fun addStudent() {
        val student = Student(
            hashMapOf(
                //TODO исправить костыль
                //"id" to 1,
                "lastName" to addStudentView.lastNameField.text,
                "firstName" to addStudentView.firstNameField.text,
                "middleName" to addStudentView.middleNameField.text,
                "phone" to (addStudentView.phoneField.text.ifEmpty { null } as Any?),
                "telegram" to (addStudentView.telegramField.text.ifEmpty { null } as Any?),
                "email" to (addStudentView.emailField.text.ifEmpty { null } as Any?),
                "github" to (addStudentView.githubField.text.ifEmpty { null } as Any?)
            )
        )
        studentList.addStudent(student)
        addStudentView.close()
        studentListController.updateTableData()
    }
}