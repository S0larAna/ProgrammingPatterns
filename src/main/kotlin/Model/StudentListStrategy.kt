package Model

interface StudentListStrategy {
    fun writeToFile(students: MutableList<Student>, filePath: String?=null)
    fun readFromFile(filepath: String?=null): MutableList<Student>
}