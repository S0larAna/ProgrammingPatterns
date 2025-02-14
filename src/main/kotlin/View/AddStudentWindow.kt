import Controller.AddStudentController
import Controller.StudentListController
import Controller.UpdateStudentController
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
    private val addStudentController: AddStudentController = AddStudentController(this, students, studentListController)
    val fields = mutableMapOf<String, TextField>()
    private val addButton = Button("Ok")

    override fun start(primaryStage: Stage) {
        this.primaryStage = primaryStage
        primaryStage.title = "Add Student"

        val grid = createForm()
        populateFields()

        fields.values.forEach { field -> field.textProperty().addListener { _, _, _ -> addStudentController.validateFields() } }

        addButton.isDisable = true
        addButton.setOnAction {
            if (student == null) {
                addStudentController.addStudent()
            } else {
                updateStudentController?.updateStudent(
                    fields["firstName"]?.text ?: "",
                    fields["lastName"]?.text ?: "",
                    fields["middleName"]?.text ?: ""
                )
            }
            primaryStage.close()
        }

        grid.add(addButton, 1, fields.size)

        val scene = Scene(grid, 400.0, 400.0)
        primaryStage.scene = scene
        primaryStage.show()
    }

    private fun createForm(): GridPane {
        val grid = GridPane()
        grid.padding = Insets(10.0)
        grid.hgap = 10.0
        grid.vgap = 10.0

        val labels = listOf("Last Name", "First Name", "Middle Name", "Phone", "Telegram", "Email", "Github")
        labels.forEachIndexed { index, label ->
            val textField = TextField()
            fields[label.replace(" ", "").decapitalize()] = textField
            grid.add(Label("$label:"), 0, index)
            grid.add(textField, 1, index)
        }

        return grid
    }

    private fun populateFields() {
        student?.let {
            fields["firstName"]?.text = it.firstName
            fields["lastName"]?.text = it.lastName
            fields["middleName"]?.text = it.middleName
            fields["phone"]?.text = it.phone ?: ""
            fields["telegram"]?.text = it.telegram ?: ""
            fields["email"]?.text = it.email ?: ""
            fields["github"]?.text = it.github ?: ""
        }

        if (updateStudentController != null) {
            fields["phone"]?.isDisable = true
            fields["telegram"]?.isDisable = true
            fields["email"]?.isDisable = true
            fields["github"]?.isDisable = true
        }
        else fields.values.forEach { it.isDisable = false }
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