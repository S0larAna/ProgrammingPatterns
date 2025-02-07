package Model

interface StudentListStrategy {
    fun writeToFile(students: MutableList<Student>)
    fun readFromFile(): MutableList<Student>

    fun addStudent(student: Student)

    fun updateStudent(id: Int, student: Student)

    fun removeStudent(id: Int)
}