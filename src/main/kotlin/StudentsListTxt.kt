import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class StudentsListTxt(private val filePath: String) {
    var students: MutableList<Student> = mutableListOf()

    init {
        students = readFromTxt(filePath)
    }

    fun writeToTxt(directoryPath: String, fileName: String) {
        val directory = File(directoryPath)
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw IOException("Failed to create directory: $directoryPath")
            }
        }

        if (!directory.isDirectory) {
            throw IllegalArgumentException("The provided path is not a directory: $directoryPath")
        }

        val file = File(directory, fileName)

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

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }

    fun get_k_n_student_short_list(k: Int, n: Int): Data_list<Student_short> {
        val startIndex = (k - 1) * n
        val endIndex = minOf(startIndex + n, students.size)
        val shortList = students.subList(startIndex, endIndex).map { Student_short(it) }
        return Data_list_student_short(shortList)
    }

    fun sortByName() {
        students.sortBy { it.getNameInfo() }
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun replaceStudent(id: Int, newStudent: Student) {
        val index = students.indexOfFirst { it.id == id }
        if (index != -1) {
            students[index] = newStudent
        }
    }

    fun removeStudent(id: Int) {
        students.removeIf { it.id == id }
    }

    fun get_student_short_count(): Int {
        return students.size
    }

    companion object{
        fun readFromTxt(filePath: String): MutableList<Student> {
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
}