package App

import Student
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class StudentListView : BorderPane() {
    private val itemsPerPage = 8
    private var currentPage = 1
    private var totalPages = 1

    init {
        padding = Insets(10.0)

        // Область фильтрации (верхняя часть)
        top = createFilterArea()

        // Таблица студентов (центральная часть)
        center = createTableArea()
    }

    private fun createTableArea(): VBox {
        val tableArea = VBox(10.0)

        val table = createStudentTable()
        val testData = listOf(
            Student("lastName:Иванов,firstName:Иван,middleName:Иванович,phone:+79001234567,telegram:@ivanov,email:ivanov@example.com,github:johndoe"),
            Student("lastName:Петров,firstName:Петр,middleName:Петрович,phone:+79007654321,telegram:@petrov,email:petrov@example.com,github:petrov"),
            Student("lastName:Сидоров,firstName:Сидор,middleName:Сидорович,phone:+79001234568,telegram:@sidorov,email:sidorov@example.com,github:sidorov"),
            Student("lastName:Кузнецов,firstName:Кузьма,middleName:Кузьмич,phone:+79001234569,telegram:@kuznetsov,email:kuznetsov@example.com,github:kuznetsov"),
            Student("lastName:Смирнов,firstName:Смирн,middleName:Смирнович,phone:+79001234570,telegram:@smirnov,email:smirnov@example.com,github:smirnov"),
            Student("lastName:Сидоров,firstName:Сидор,middleName:Сидорович,phone:+79001234568,telegram:@sidorov,email:sidorov@example.com,github:sidorov"),
            Student("lastName:Кузнецов,firstName:Кузьма,middleName:Кузьмич,phone:+79001234569,telegram:@kuznetsov,email:kuznetsov@example.com,github:kuznetsov"),
            Student("lastName:Смирнов,firstName:Смирн,middleName:Смирнович,phone:+79001234570,telegram:@smirnov,email:smir@example.com,github:smirnov"),
            Student("lastName:Сидоров,firstName:Сидор,middleName:Сидорович,phone:+79001234568,telegram:@sidorov,email:sidorov@example.com,github:sidorov"),
            Student("lastName:Кузнецов,firstName:Кузьма,middleName:Кузьмич,phone:+79001234569,telegram:@kuznetsov,email:kuznetsov@example.com,github:kuznetsov"),
            Student("lastName:Смирнов,firstName:Смирн,middleName:Смирнович,phone:+79001234570,telegram:@smirnov,email:smirnov@example.com,github:smirnov")
        )
        totalPages = Math.ceil(testData.size / itemsPerPage.toDouble()).toInt()
        updateTableData(table, testData)

        val paginationControls = createPaginationControls(table, testData)
        val controlArea = createControlArea(table)

        tableArea.children.addAll(table, paginationControls, controlArea)
        return tableArea
    }

    private fun createFilterArea(): VBox {
        val filterArea = VBox(10.0)
        filterArea.padding = Insets(0.0, 0.0, 10.0, 0.0)

        val nameFilter = createNameFilter()
        val gitFilter = createContactFilter("Git")
        val emailFilter = createContactFilter("Email")
        val phoneFilter = createContactFilter("Телефон")
        val telegramFilter = createContactFilter("Telegram")

        filterArea.children.addAll(
            nameFilter,
            gitFilter,
            emailFilter,
            phoneFilter,
            telegramFilter
        )

        return filterArea
    }

    private fun createNameFilter(): HBox {
        val nameBox = HBox(10.0)
        val nameLabel = Label("Фамилия и инициалы:")
        val nameField = TextField()
        nameBox.children.addAll(nameLabel, nameField)
        return nameBox
    }

    private fun createContactFilter(contactType: String): VBox {
        val contactBox = VBox(5.0)

        val label = Label(contactType)
        label.style = "-fx-font-weight: bold;"

        val optionsAndSearchBox = HBox(10.0)

        val options = FXCollections.observableArrayList("Да", "Нет", "Не важно")
        val comboBox = ComboBox(options)
        comboBox.selectionModel.select("Не важно")
        comboBox.prefWidth = 150.0

        val searchField = TextField()
        searchField.isDisable = true
        searchField.prefWidth = 200.0

        comboBox.setOnAction {
            searchField.isDisable = comboBox.value != "Да"
        }

        optionsAndSearchBox.children.addAll(comboBox, searchField)

        contactBox.children.addAll(label, optionsAndSearchBox)

        return contactBox
    }

    private fun createStudentTable(): TableView<Student> {
        val table = TableView<Student>()

        val idColumn = TableColumn<Student, Int>("ID")
        idColumn.setCellValueFactory { cellData -> SimpleObjectProperty(cellData.value.id) }

        val lastNameColumn = TableColumn<Student, String>("Фамилия")
        lastNameColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.lastName) }

        val nameColumn = TableColumn<Student, String>("Имя")
        nameColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.firstName) }

        val middleNameColumn = TableColumn<Student, String>("Отчество")
        middleNameColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.middleName) }

        val gitColumn = TableColumn<Student, String>("Git")
        gitColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.github) }

        val emailColumn = TableColumn<Student, String>("Email")
        emailColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.email) }

        val phoneColumn = TableColumn<Student, String>("Телефон")
        phoneColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.phone) }

        val telegramColumn = TableColumn<Student, String>("Telegram")
        telegramColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.telegram) }

        table.columns.addAll(idColumn, lastNameColumn, nameColumn, middleNameColumn, gitColumn, emailColumn, phoneColumn, telegramColumn)
        table.isEditable = false
        table.selectionModel.selectionMode = SelectionMode.MULTIPLE

        return table
    }

    private fun updateTableData(table: TableView<Student>, data: List<Student>) {
        val fromIndex = (currentPage - 1) * itemsPerPage
        val toIndex = Math.min(fromIndex + itemsPerPage, data.size)
        table.items.setAll(data.subList(fromIndex, toIndex))
    }

    private fun createControlArea(table: TableView<Student>): HBox {
        val controlArea = HBox(10.0)
        controlArea.padding = Insets(10.0, 0.0, 0.0, 0.0)

        val addButton = Button("Добавить")
        val editButton = Button("Изменить")
        val deleteButton = Button("Удалить")
        val updateButton = Button("Обновить")

        addButton.styleClass.add("action-button")
        editButton.styleClass.add("action-button")
        deleteButton.styleClass.add("action-button")
        updateButton.styleClass.add("action-button")

        editButton.isDisable = true
        deleteButton.isDisable = true

        controlArea.children.addAll(addButton, editButton, deleteButton, updateButton)

        table.selectionModel.selectedItems.addListener(ListChangeListener { change ->
            val selectedItems = table.selectionModel.selectedItems
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

        return controlArea
    }

    private fun createPaginationControls(table: TableView<Student>, data: List<Student>): HBox {
        val controls = HBox(10.0)

        val prevButton = Button("Предыдущая")
        val nextButton = Button("Следующая")
        val pageInfo = Label()

        prevButton.setOnAction {
            if (currentPage > 1) {
                currentPage--
                updateTableData(table, data)
                updatePageInfo(pageInfo)
            }
        }

        nextButton.setOnAction {
            if (currentPage < totalPages) {
                currentPage++
                updateTableData(table, data)
                updatePageInfo(pageInfo)
            }
        }

        controls.children.addAll(prevButton, pageInfo, nextButton)
        return controls
    }

    private fun updatePageInfo(label: Label) {
        label.text = "Страница $currentPage из $totalPages"
    }
}