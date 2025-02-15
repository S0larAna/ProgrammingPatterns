package Controller

import AddStudentWindow
import DBConnection.DatabaseManager
import DBConnection.Students_list_DB
import Model.*
import View.StudentListView
import javafx.collections.FXCollections
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
    }

    fun updateTableData() {
        try {
            students.readFromFile()
        }
        catch (e: Exception) {
            println(e.message)
            studentListView.showErrorAlert("Ошибка чтения информации о студентах", "Возникла ошибка при чтении из файла")
        }
        getFilters()
        val studentList = students.get_k_n_student_short_list(paginationState.currentPage, paginationState.itemsPerPage, filterSubstrings, filters)
        paginationState.updateTotalPages(students.get_student_short_count())
        paginationState.updatePageInfo(studentListView)
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

    fun refreshTable() {
        paginationState.updateCurrentPage(1)
        updateTableData()
    }

    fun updateStrategy(type: String) {
        if (!::students.isInitialized) {
            students = StudentList(selectStrategy(type))
        }
        else {
            students.setStrategy(selectStrategy(type))
            students.readFromFile()
        }
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
                    studentListView.showErrorAlert("db connection error", "Возникла ошибка при подключении к базе данных")
                    throw e
                }
            }
            else -> throw IllegalArgumentException("Unknown strategy type")
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
            filterSubstrings[key] = studentListView.getFilterTextFieldValue(index)
            filters["has${key.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }.removeSuffix("Substring")}"] = FilterOption.fromText(studentListView.getFilterComboBoxValue(index))
        }

        filterSubstrings["initialsSubstring"] = studentListView.getNameFilterValue()
    }
}