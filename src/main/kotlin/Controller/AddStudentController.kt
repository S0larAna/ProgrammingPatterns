package Controller

import AddStudentWindow
import Model.Student
import Model.StudentBase
import Model.StudentList

class AddStudentController(private val addStudentView: AddStudentWindow, private val studentList: StudentList, private val studentListController: StudentListController) {

    fun validateFields() {
        val fields = mapOf<String, (String) -> Boolean>(
            "lastName" to StudentBase::isValidName,
            "firstName" to StudentBase::isValidName,
            "middleName" to StudentBase::isValidName,
            "phone" to StudentBase::isValidPhoneNumber,
            "telegram" to StudentBase::isValidTelegramHandle,
            "email" to StudentBase::isValidEmail,
            "github" to StudentBase::isValidGithubUsername
        )

        var allValid = true
        for ((key, validator) in fields) {
            val field = addStudentView.fields[key]
            val isValid = validator(field?.text ?: "")
            addStudentView.updateValidationState(isValid, field!!)
            if (!isValid) allValid = false
        }
        addStudentView.updateOkButtonState(allValid)
    }

    fun addStudent() {
        val student = Student(
            hashMapOf(
                "lastName" to addStudentView.fields["lastName"]?.text,
                "firstName" to addStudentView.fields["firstName"]?.text,
                "middleName" to addStudentView.fields["middleName"]?.text,
                "phone" to addStudentView.fields["phone"]?.text?.ifEmpty { null },
                "telegram" to addStudentView.fields["telegram"]?.text?.ifEmpty { null },
                "email" to addStudentView.fields["email"]?.text?.ifEmpty { null },
                "github" to addStudentView.fields["github"]?.text?.ifEmpty { null }
            )
        )
        studentList.addStudent(student)
        addStudentView.close()
        studentListController.updateTableData()
    }
}