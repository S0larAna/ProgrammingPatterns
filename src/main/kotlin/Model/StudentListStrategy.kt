package Model

interface StudentListStrategy {
    fun writeToFile(students: MutableList<Student>)
    fun readFromFile(): MutableList<Student>
}