package Model

import java.io.File
import org.yaml.snakeyaml.Yaml

class StudentsListYAML(var filePath: String?): StudentListStrategy {
    private var students: MutableList<Student> = mutableListOf()

    override fun readFromFile(): MutableList<Student> {
        if (filePath == null) {
            throw IllegalArgumentException("File path is null")
        }
        val yaml = Yaml()
        val students = mutableListOf<Student>()
        try {
            val inputStream = File(filePath).inputStream()
            val yamlMap = yaml.load<Map<String, List<HashMap<String, Any?>>>>(inputStream)
            yamlMap["students"]?.forEach{
                println(it)
                students.add(Student(it))
            }
        } catch (e: Exception) {
            println("Error reading file: ${e.message}")
        }
        return students
    }

    override fun writeToFile(students: MutableList<Student>) {
        try {
            if (filePath == null) {
                throw IllegalArgumentException("File path is null")
            }
            File(filePath).printWriter().use { out ->
                out.println("students:")
                students.forEach { student ->
                    out.println("- id: ${student.id}")
                    out.println("  - lastName: \"${student.lastName}\"")
                    out.println("  firstName: \"${student.firstName}\"")
                    out.println("  middleName: \"${student.middleName}\"")
                    student.phone?.let { out.println("  phone: \"$it\"") }
                    student.telegram?.let { out.println("  telegram: \"$it\"") }
                    student.email?.let { out.println("  email: \"$it\"") }
                    student.github?.let { out.println("  github: \"$it\"") }
                    out.println()
                }
            }
        } catch (e: Exception) {
            println("Error reading file: ${e.message}")
        }
    }
}