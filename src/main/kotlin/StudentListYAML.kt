import java.io.File

class Students_list_YAML(): StudentListStrategy {
    private var students: MutableList<Student> = mutableListOf()

    override fun readFromFile(filePath: String): MutableList<Student> {
        try {
            val lines = File(filePath).readLines()
            var currentStudent = HashMap<String, Any?>()

            for (line in lines) {
                if (line.trim().startsWith("-")) {
                    if (currentStudent.isNotEmpty()) {
                        students.add(Student(currentStudent))
                        currentStudent.clear()
                    }
                } else {
                    val parts = line.trim().split(":", limit = 2)
                    if (parts.size == 2) {
                        currentStudent[parts[0].trim()] = parts[1].trim()
                    }
                }
            }
            return students
        } catch (e: Exception) {
            println("Ошибка при чтении файла: ${e.message}")
            return students
        }
    }

    override fun writeToFile(students: MutableList<Student>, filePath: String) {
        try {
            File(filePath).printWriter().use { out ->
                students.forEach { student ->
                    out.println("- id: ${student.id}")
                    out.println("  lastName: ${student.lastName}")
                    out.println("  firstName: ${student.firstName}")
                    out.println("  middleName: ${student.middleName}")
                    student.phone?.let { out.println("  phone: $it") }
                    student.telegram?.let { out.println("  telegram: $it") }
                    student.email?.let { out.println("  email: $it") }
                    student.github?.let { out.println("  github: $it") }
                    out.println()
                }
            }
        } catch (e: Exception) {
            println("Ошибка при записи в файл: ${e.message}")
        }
    }
}