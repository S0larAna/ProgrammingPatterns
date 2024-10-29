import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class StudentsListTxt(): StudentListStrategy {
    override fun writeToFile(students: MutableList<Student>, filePath: String) {
        val file = File(filePath)

        try {
            file.bufferedWriter().use { writer ->
                students.forEach { student ->
                    val line = student.toFileString()
                    writer.write(line)
                    writer.newLine()
                }
            }
            println("Successfully wrote ${students.size} students to ${file.absolutePath}")
        } catch (e: IOException) {
            throw IOException("Error writing to file: ${file.absolutePath}", e)
        }
    }
    override fun readFromFile(filePath: String): MutableList<Student> {
        val students = mutableListOf<Student>()
        try {
            val file = File(filePath)

            if (!file.exists()) {
                throw FileNotFoundException("File not found: $filePath")
            }

            if (!file.isFile || !file.canRead()) {
                throw IllegalArgumentException("Invalid file or no read permissions: $filePath")
            }

            file.useLines { lines ->
                for (line in lines) {
                    try {
                        val student = Student(line)
                        students.add(student)
                    } catch (e: IllegalArgumentException) {
                        println("Error parsing line: $line")
                        println("Error message: ${e.message}")
                    }
                }
            }

        } catch (e: FileNotFoundException) {
            println("Файл не найден: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("Ошибка при чтении файла: ${e.message}")
        }
        return students
    }
}