package Controller

import AddStudentWindow
import DBConnection.DatabaseManager
import DBConnection.Students_list_DB
import Model.*
import View.StudentListView
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class StudentListController(private val studentListView: StudentListView) {
    private lateinit var students: StudentList
    private var dataListStudentShort: Data_list_student_short
    var filterSubstrings = mutableMapOf<String, String?>("gitSubstring" to null, "nameSubstring" to null, "emailSubstring" to null, "telegramSubstring" to null, "phoneSubstring" to null)
    var filters = mutableMapOf<String, String?>("hasGit" to "Не важно", "hasEmail" to "Не важно", "hasTelegram" to "Не важно", "hasPhone" to "Не важно")
    var currentPage = 1
    val itemsPerPage = 6
    var totalPages = 1

    init {
        this.getFilters()
        this.updateStrategy("database")
        totalPages = Math.ceil(students.get_student_short_count() / itemsPerPage.toDouble()).toInt()
        students.addObserver(studentListView)
        dataListStudentShort = Data_list_student_short(students.get_k_n_student_short_list(currentPage, itemsPerPage, filterSubstrings, filters))
        setupPaginationControls()
        setupControlArea()
    }

    fun updateTableData() {
        try {
            students.readFromFile()
        }
        catch (e: Exception) {
            println(e.message)
            showErrorAlert("Ошибка чтения информации о студентах", "Возникла ошибка при чтении из файла")
        }
        val studentList = students.get_k_n_student_short_list(currentPage, itemsPerPage, filterSubstrings, filters)
        totalPages = Math.ceil(students.get_student_short_count() / itemsPerPage.toDouble()).toInt()
        updatePageInfo(studentListView.paginationControls.children[1] as Label)
        dataListStudentShort = Data_list_student_short(studentList)
        val studentObservableList = FXCollections.observableArrayList(studentList)
        dataListStudentShort.notify(studentObservableList, this.studentListView.table)
    }

    private fun setupPaginationControls() {
        val prevButton = studentListView.paginationControls.children[0] as Button
        val pageInfo = studentListView.paginationControls.children[1] as Label
        val nextButton = studentListView.paginationControls.children[2] as Button

        prevButton.setOnAction {
            if (currentPage > 1) {
                currentPage--
                updateTableData()
                updatePageInfo(pageInfo)
            }
        }

        nextButton.setOnAction {
            if (currentPage < totalPages) {
                currentPage++
                updateTableData()
                updatePageInfo(pageInfo)
            }
        }
        updatePageInfo(pageInfo)
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
                }
                StudentListDBAdapter(studentDb)
            }
            else -> throw IllegalArgumentException("Unknown strategy type")
        }
    }

    fun updatePageInfo(label: Label) {
        label.text = "Страница $currentPage из $totalPages"
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
            println("Add button clicked")
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
            currentPage=1
            val selectedStudents = studentListView.table.selectionModel.selectedItems

            selectedStudents.forEach { student ->
                students.removeStudent(student.id)
                totalPages = Math.ceil(students.get_student_short_count() / itemsPerPage.toDouble()).toInt()
            }
        }
        updateButton.setOnAction {
            currentPage=1
            getFilters()
            updateTableData()
        }
    }

    private fun getFilters(){
        filterSubstrings["gitSubstring"] = (((studentListView.filterArea.children[1] as VBox).children[1] as HBox).children[1] as TextField).text
        filterSubstrings["emailSubstring"] = (((studentListView.filterArea.children[2] as VBox).children[1] as HBox).children[1] as TextField).text
        filterSubstrings["phoneSubstring"] = (((studentListView.filterArea.children[3] as VBox).children[1] as HBox).children[1] as TextField).text
        filterSubstrings["telegramSubstring"] = (((studentListView.filterArea.children[4] as VBox).children[1] as HBox).children[1] as TextField).text
        filterSubstrings["initialsSubstring"] = ((studentListView.filterArea.children[0] as HBox).children[1] as TextField).text
        filters["hasGit"] = (((studentListView.filterArea.children[1] as VBox).children[1] as HBox).children[0] as ComboBox<String>).value
        filters["hasEmail"] = (((studentListView.filterArea.children[2] as VBox).children[1] as HBox).children[0] as ComboBox<String>).value
        filters["hasPhone"] = (((studentListView.filterArea.children[3] as VBox).children[1] as HBox).children[0] as ComboBox<String>).value
        filters["hasTelegram"] = (((studentListView.filterArea.children[4] as VBox).children[1] as HBox).children[0] as ComboBox<String>).value
    }

    private fun showErrorAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}