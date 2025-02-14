package Model

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class StudentsListTxt(var filePath: String?): BaseStudentListFile() {
    override fun writeToFile(students: MutableList<Student>) {
        if (filePath == null) {
            throw IllegalArgumentException("File path is null")
        }
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
    override fun readFromFile(): MutableList<Student> {
        if (filePath == null) {
            throw IllegalArgumentException("File path is null")
        }
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
            println("File not found: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("Error while reading: ${e.message}")
        }
        return students
    }
}