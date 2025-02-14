package View

import Controller.StudentListController
import Model.Observer
import Model.Student_short
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class StudentListView: VBox(), Observer {
    val table: TableView<Student_short> = createStudentTable()
    val filterArea: VBox = createFilterArea()
    val controlArea: HBox = createControlArea()
    val paginationControls: HBox = createPaginationControls()
    private val dataSourceComboBox = ComboBox<String>()

    val controller: StudentListController = StudentListController(this)
    init {
        dataSourceComboBox.items.addAll("Database", "JSON", "YAML", "TXT")
        dataSourceComboBox.value = "Database"
        dataSourceComboBox.setOnAction {
            controller.updateStrategy(dataSourceComboBox.value)
        }
        controller.updateTableData()
        padding = Insets(10.0)
        spacing = 10.0
        children.addAll(dataSourceComboBox, filterArea, table, paginationControls, controlArea)
    }

    private fun createStudentTable(): TableView<Student_short> {
        val table = TableView<Student_short>()

        val idColumn = TableColumn<Student_short, Int>("ID")
        idColumn.setCellValueFactory { cellData -> SimpleObjectProperty(cellData.value.id) }

        val nameColumn = TableColumn<Student_short, String>("ФИО")
        nameColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.nameWithInitials) }

        val gitColumn = TableColumn<Student_short, String>("Git")
        gitColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.github) }

        val contactsColumn = TableColumn<Student_short, String>("Контакты")
        contactsColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.contact) }

        table.columns.addAll(idColumn, nameColumn, gitColumn, contactsColumn)
        table.isEditable = false
        table.selectionModel.selectionMode = SelectionMode.MULTIPLE

        return table
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

    private fun createControlArea(): HBox {
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

        this.table.selectionModel.selectedItems.addListener(ListChangeListener { change ->
            val selectedItems = this.table.selectionModel.selectedItems
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
            controller.addStudent()
        }

        editButton.setOnAction {
            controller.editStudent(this.table.selectionModel.selectedItem.id)
        }

        deleteButton.setOnAction {
            val selectedItems = this.table.selectionModel.selectedItems
            var selectedItemsIds = mutableListOf<Int>()
            for (item in selectedItems) {
                selectedItemsIds.add(item.id)
            }
            controller.deleteStudent(selectedItemsIds)
        }

        updateButton.setOnAction {
            controller.updateTableData()
        }

        controlArea.children.addAll(addButton, editButton, deleteButton, updateButton)

        return controlArea
    }

    private fun createPaginationControls(): HBox {
        val controls = HBox(10.0)

        val prevButton = Button("Предыдущая")
        val nextButton = Button("Следующая")
        val pageLabel = Label()

        prevButton.setOnAction {
            controller.prevPage()
        }

        nextButton.setOnAction {
            controller.nextPage()
        }

        controls.children.addAll(prevButton, pageLabel, nextButton)
        return controls
    }

    override fun updatePageInfo(currentPage: Int, totalPages: Int) {
        (paginationControls.children[1] as Label).text = "Страница $currentPage из $totalPages"
    }


    override fun setTableData(dataTable: List<Student_short>) {
        val studentObservableList = FXCollections.observableArrayList(dataTable)
        table.items.setAll(studentObservableList)
    }
}