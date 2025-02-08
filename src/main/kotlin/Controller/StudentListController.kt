package Controller

import AddStudentWindow
import DBConnection.DatabaseManager
import DBConnection.Students_list_DB
import Model.*
import View.StudentListView
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*

class StudentListController(private val studentListView: StudentListView) {
    private lateinit var students: StudentList
    private var dataListStudentShort: Data_list_student_short
    var filterSubstrings = mutableMapOf<String, String?>("gitSubstring" to null, "nameSubstring" to null, "emailSubstring" to null, "telegramSubstring" to null, "phoneSubstring" to null)
    var filters = mutableMapOf<String, FilterOption?>("hasGit" to FilterOption.NO_MATTER, "hasEmail" to FilterOption.NO_MATTER, "hasTelegram" to FilterOption.NO_MATTER, "hasPhone" to FilterOption.NO_MATTER)
    var paginationState = PaginationState()

    init {
        this.getFilters()
        this.updateStrategy("database")
        students.addObserver(studentListView)
        dataListStudentShort = Data_list_student_short(students.get_k_n_student_short_list(paginationState.currentPage, paginationState.itemsPerPage, filterSubstrings, filters))
        //setupPaginationControls()
        //setupControlArea()
    }

    fun updateTableData() {
        try {
            students.readFromFile()
            paginationState.updateTotalPages(students.get_student_short_count())
            paginationState.updatePageInfo(studentListView)
        }
        catch (e: Exception) {
            println(e.message)
            showErrorAlert("Ошибка чтения информации о студентах", "Возникла ошибка при чтении из файла")
        }
        val studentList = students.get_k_n_student_short_list(paginationState.currentPage, paginationState.itemsPerPage, filterSubstrings, filters)
        //totalPages = Math.ceil(students.get_student_short_count() / itemsPerPage.toDouble()).toInt()
        //updatePageInfo(studentListView.paginationControls.children[1] as Label)
        dataListStudentShort = Data_list_student_short(studentList)
        val studentObservableList = FXCollections.observableArrayList(studentList)
        dataListStudentShort.notify(studentObservableList, this.studentListView.table)
    }

    fun prevPage() {
        if (paginationState.currentPage > 1) {
            paginationState.currentPage--
            updateTableData()
        }
    }

    fun nextPage() {
        if (paginationState.currentPage < paginationState.totalPages) {
            paginationState.currentPage++
            updateTableData()
        }
    }

    fun updateStrategy(type: String) {
        students = StudentList(selectStrategy(type))
        updateTableData()
    }

    private fun selectStrategy(type: String): StudentListStrategy {
        return when (type.toLowerCase()) {
            "json" -> StudentsListJSON("./src/main/resources/students.json")
            "yaml" -> StudentsListYAML("./src/main/resources/students.yaml")
            "txt" -> StudentsListTxt("./src/main/resources/students.txt")
            "database" -> {
                val dbConnection = DatabaseManager
                val studentDb = Students_list_DB(dbConnection)
                try{
                    dbConnection.connect()
                    if (dbConnection.connection==null){
                        throw Exception("db connection error")
                    }
                    StudentListDBAdapter(studentDb)
                }
                catch (e: Exception) {
                    showErrorAlert("db connection error", "Возникла ошибка при подключении к базе данных")
                    throw e
                }
            }
            else -> throw IllegalArgumentException("Unknown strategy type")
        }
    }

    private fun setupControlArea() {
        val addButton = studentListView.controlArea.children[0] as Button
        val editButton = studentListView.controlArea.children[1] as Button
        val deleteButton = studentListView.controlArea.children[2] as Button
        val updateButton = studentListView.controlArea.children[3] as Button

        studentListView.table.selectionModel.selectedItems.addListener(ListChangeListener { change ->
            val selectedItems = studentListView.table.selectionModel.selectedItems
            when {
                selectedItems.size == 1 -> {
                    editButton.isDisable = false
                    deleteButton.isDisable = false
                }
                selectedItems.size > 1 -> {
                    editButton.isDisable = true
                    deleteButton.isDisable = false
                }
                else -> {
                    editButton.isDisable = true
                    deleteButton.isDisable = true
                }
            }
        })

        addButton.setOnAction {
            val addStudentWindow = AddStudentWindow(students, this)
            addStudentWindow.start(Stage())
        }
        editButton.setOnAction {
            val selectedStudentId = studentListView.table.selectionModel.selectedItem.id
            val selectedStudent = students.getStudentById(selectedStudentId)
            val addStudentWindow = AddStudentWindow(students, this, selectedStudent,
                selectedStudent?.let { it1 -> UpdateStudentController(it1, students, this) })
            addStudentWindow.start(Stage())
        }
        deleteButton.setOnAction {
            val selectedStudents = studentListView.table.selectionModel.selectedItems

            selectedStudents.forEach { student ->
                students.removeStudent(student.id)}
            updateTableData()
        }
        updateButton.setOnAction {
            getFilters()
            updateTableData()
        }
    }

    fun deleteStudent(selectedIds: MutableList<Int>) {
        paginationState.currentPage=1
        selectedIds.forEach { id ->
            students.removeStudent(id)
        }
        paginationState.updatePageInfo(studentListView)
        updateTableData()
    }

    fun addStudent() {
        val addStudentWindow = AddStudentWindow(students, this)
        addStudentWindow.start(Stage())
    }

    fun editStudent(selectedStudentId: Int){
        val selectedStudent = students.getStudentById(selectedStudentId)
        val addStudentWindow = AddStudentWindow(students, this, selectedStudent,
            selectedStudent?.let { it1 -> UpdateStudentController(it1, students, this) })
        addStudentWindow.start(Stage())
    }

    private fun getFilters(){
        val filterFields = listOf(
            "gitSubstring" to 1,
            "emailSubstring" to 2,
            "phoneSubstring" to 3,
            "telegramSubstring" to 4
        )

        filterFields.forEach { (key, index) ->
            filterSubstrings[key] = (((studentListView.filterArea.children[index] as VBox).children[1] as HBox).children[1] as TextField).text
            filters["has${
                key.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }.removeSuffix("Substring")}"] = FilterOption.fromText((((studentListView.filterArea.children[index] as VBox).children[1] as HBox).children[0] as ComboBox<String>).value)
        }

        filterSubstrings["initialsSubstring"] = ((studentListView.filterArea.children[0] as HBox).children[1] as TextField).text
    }

    private fun showErrorAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}