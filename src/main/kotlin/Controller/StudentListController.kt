package Controller

import AddStudentWindow
import DBConnection.DatabaseManager
import DBConnection.Students_list_DB
import Model.Data_list_student_short
import Model.StudentList
import Model.StudentListDBAdapter
import View.StudentListView
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.stage.Stage

class StudentListController(private val studentListView: StudentListView) {
    private var students: StudentList
    private var dataListStudentShort: Data_list_student_short
    val dbConnection = DatabaseManager
    val studentDb = Students_list_DB(dbConnection)
    private var currentPage = 1
    private val itemsPerPage = 7
    private var totalPages = 1

    init {
        try {
            dbConnection.connect()
            if (dbConnection.connection==null){
                throw Exception("db connection error")
            }
        }
        catch (e: Exception) {
            showErrorAlert("db connection error", "Возникла ошибка при подключении к базе данных")
        }
        System.out.println(studentDb.getStudentById(1))
        students = StudentList(StudentListDBAdapter(Students_list_DB(dbConnection)))
        students.readFromFile("students")
        totalPages = Math.ceil(students.get_student_short_count() / itemsPerPage.toDouble()).toInt()
        students.addObserver(studentListView)
        dataListStudentShort = Data_list_student_short(students.get_k_n_student_short_list(currentPage, itemsPerPage))
        //TODO: избавиться от костыля с пробросом пути к файлу
        setupPaginationControls()
        setupControlArea()
    }

    fun updateTableData() {
        try {
            students.readFromFile("students")
        }
        catch (e: Exception) {
            println(e.message)
            showErrorAlert("Ошибка чтения информации о студентах", "Возникла ошибка при чтении из файла")
        }
        totalPages = Math.ceil(students.get_student_short_count() / itemsPerPage.toDouble()).toInt()
        val studentList = students.get_k_n_student_short_list(currentPage, itemsPerPage)
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
                selectedStudent?.let { it1 -> UpdateStudentController(it1, students, this, dbConnection) })
            addStudentWindow.start(Stage())
        }
        deleteButton.setOnAction {
            val selectedStudents = studentListView.table.selectionModel.selectedItems

            selectedStudents.forEach { student ->
                students.removeStudent(student.id)
                studentDb.deleteStudent(student.id)
            }
        }
        updateButton.setOnAction {
            //TODO: реализовать обновление таблицы
        }
    }

    private fun showErrorAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}