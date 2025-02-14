package Model

import javafx.collections.ObservableList
import javafx.scene.control.TableView

class Data_list_student_short(var students: List<Student_short>) : Data_list<Student_short>(students), Subject {
    private val observers = mutableListOf<Observer>()
    override fun getNames(): Array<Any?> {
        return arrayOf(arrayOf("id","name with initials", "github", "contact"))
    }

    override fun getRows(): Array<Array<Any?>> {
        val rows = Array(data.size) { index ->
            val student = data[index]
            arrayOf<Any?>(
                student.id,
                student.nameWithInitials,
                student.github,
                student.contact
            )
        }
        return rows
    }

    fun notify(observableList: ObservableList<Student_short>, table: TableView<Student_short>) {
        observableList.setAll(data)
        table.items.setAll(observableList)
    }

    override fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        for (observer in observers) {
            observer.setTableData(students)
        }
    }
}