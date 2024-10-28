import java.io.File

class Students_list_YAML(private val filePath: String) {
    private var students: MutableList<Student> = mutableListOf()
    private var lastId: Int = 0

    init {
        readFromFile()
    }

    private fun readFromFile() {
        try {
            val lines = File(filePath).readLines()
            var currentStudent: MutableMap<String, String> = mutableMapOf()

            for (line in lines) {
                if (line.trim().startsWith("-")) {
                    if (currentStudent.isNotEmpty()) {
                        addStudentFromMap(currentStudent)
                        currentStudent.clear()
                    }
                } else {
                    val parts = line.trim().split(":", limit = 2)
                    if (parts.size == 2) {
                        currentStudent[parts[0].trim()] = parts[1].trim()
                    }
                }
            }

            if (currentStudent.isNotEmpty()) {
                addStudentFromMap(currentStudent)
            }
        } catch (e: Exception) {
            println("Ошибка при чтении файла: ${e.message}")
        }
    }

    private fun addStudentFromMap(map: Map<String, String>) {
        val id = map["id"]?.toIntOrNull() ?: ++lastId
        lastId = maxOf(lastId, id)
        val student = Student(
            id = id,
            lastName = map["lastName"] ?: "",
            firstName = map["firstName"] ?: "",
            middleName = map["middleName"] ?: "",
            phone = map["phone"],
            telegram = map["telegram"],
            email = map["email"],
            github = map["github"]
        )
        students.add(student)
    }

    // b. Запись всех значений в файл
    fun writeToFile() {
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

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }

    fun get_k_n_student_short_list(k: Int, n: Int): List<Student_short> {
        val startIndex = (k - 1) * n
        val endIndex = minOf(startIndex + n, students.size)
        return students.subList(startIndex, endIndex).map { Student_short(it) }
    }

    fun sortByName() {
        students.sortBy { "${it.lastName}${it.firstName?.get(0)}${it.middleName?.get(0)}" }
    }

    fun addStudent(student: Student) {
        val newId = lastId + 1
        students.add(student)
        lastId = newId
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
}