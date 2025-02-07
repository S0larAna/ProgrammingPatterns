import Controller.AddStudentController
import Controller.StudentListController
import Controller.UpdateStudentController
import Model.Data_list_student_short
import Model.Student
import Model.StudentList
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class AddStudentWindow(
    students: StudentList,
    studentListController: StudentListController,
    private val student: Student? = null,
    private val updateStudentController: UpdateStudentController? = null
) : Application() {
    private lateinit var primaryStage: Stage
    private var addStudentController: AddStudentController
    val githubField = TextField()
    val lastNameField = TextField()
    val firstNameField = TextField()
    val middleNameField = TextField()
    val phoneField = TextField()
    val telegramField = TextField()
    val emailField = TextField()
    val fields = listOf(githubField, lastNameField, firstNameField, middleNameField, phoneField, telegramField, emailField)
    val addButton = Button("Ok")

    init {
        addStudentController = AddStudentController(this, students, studentListController)
    }

    override fun start(primaryStage: Stage) {
        this.primaryStage = primaryStage
        primaryStage.title = "Add Student"

        val grid = GridPane()
        grid.padding = Insets(10.0)
        grid.hgap = 10.0
        grid.vgap = 10.0

        val lastNameLabel = Label("Last Name:")
        grid.add(lastNameLabel, 0, 0)
        grid.add(lastNameField, 1, 0)

        val firstNameLabel = Label("First Name:")
        grid.add(firstNameLabel, 0, 1)
        grid.add(firstNameField, 1, 1)

        val middleNameLabel = Label("Middle Name:")
        grid.add(middleNameLabel, 0, 2)
        grid.add(middleNameField, 1, 2)

        val phoneLabel = Label("Phone:")
        grid.add(phoneLabel, 0, 3)
        grid.add(phoneField, 1, 3)

        val telegramLabel = Label("Telegram:")
        grid.add(telegramLabel, 0, 4)
        grid.add(telegramField, 1, 4)

        val emailLabel = Label("Email:")
        grid.add(emailLabel, 0, 5)
        grid.add(emailField, 1, 5)

        val githubLabel = Label("GitHub:")
        grid.add(githubLabel, 0, 6)
        grid.add(githubField, 1, 6)

        if (student != null) {
            firstNameField.text = student.firstName
            lastNameField.text = student.lastName
            middleNameField.text = student.middleName
            phoneField.text = if (student.phone != null) student.phone else ""
            telegramField.text = if (student.telegram != null) student.telegram else ""
            emailField.text = if (student.email != null) student.email else ""
            githubField.text = if (student.github != null) student.github else ""
        }

        phoneField.isDisable=true
        telegramField.isDisable=true
        emailField.isDisable=true
        githubField.isDisable=true

        fields.forEach { field -> field.textProperty().addListener { _, _, _ -> addStudentController.validateFields() } }

        addButton.isDisable = true
        grid.add(addButton, 1, 7)

        addButton.setOnAction {
            if (student == null) {
                addStudentController.addStudent()
            } else {
                updateStudentController?.updateStudent(firstNameField.text, lastNameField.text, middleNameField.text)
            }
            primaryStage.close()
        }

        val scene = Scene(grid, 400.0, 400.0)
        primaryStage.scene = scene
        primaryStage.show()
    }

    fun updateOkButtonState(allValid: Boolean) {
        addButton.isDisable = !allValid
    }

    fun updateValidationState(isValid: Boolean, field: TextField) {
        field.style = if (isValid) "-fx-border-color: green;" else "-fx-border-color: red;"
    }

    fun close() {
        primaryStage.close()
    }
}