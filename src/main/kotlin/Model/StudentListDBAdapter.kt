package Model

import DBConnection.Students_list_DB

class StudentListDBAdapter(private val dbList: Students_list_DB) : StudentListStrategy {
    override fun readFromFile(): MutableList<Student> {
        return dbList.readStudents()
    }

    override fun writeToFile(students: MutableList<Student>) {
        dbList.writeStudents(students)
    }
}
