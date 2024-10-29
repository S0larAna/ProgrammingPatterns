interface StudentListStrategy {
    fun writeToFile(students: MutableList<Student>, filePath: String)
    fun readFromFile(filepath: String): MutableList<Student>
}