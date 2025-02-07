package Model

import DBConnection.Students_list_DB

class StudentListDBAdapter(private val dbList: Students_list_DB) : StudentListStrategy {
    override fun readFromFile(): MutableList<Student> {
        return dbList.readStudents()
    }

    override fun writeToFile(students: MutableList<Student>) {
        dbList.writeStudents(students)
    }

    override fun addStudent(student: Student) {
        dbList.addStudent(student)
    }

    override fun updateStudent(id: Int, student: Student) {
        dbList.updateStudent(id, student)
    }

    override fun removeStudent(id: Int) {
        dbList.deleteStudent(id)
    }
}
