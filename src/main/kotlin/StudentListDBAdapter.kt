import DBConnection.Students_list_DB

class StudentListDBAdapter(private val dbList: Students_list_DB) : StudentListStrategy {
    override fun readFromFile(filePath: String): MutableList<Student> {
        return dbList.readStudents()
    }

    override fun writeToFile(students: MutableList<Student>, filePath: String) {
        dbList.writeStudents(students)
    }
}
