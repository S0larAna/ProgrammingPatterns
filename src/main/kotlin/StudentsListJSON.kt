import kotlinx.serialization.json.*
import java.io.File

class StudentsListJSON(private val filePath: String) {
    private var students: MutableList<Student> = mutableListOf()
    private val json = Json { ignoreUnknownKeys = true }

    init {
        readFromFile()
    }

    private fun readFromFile() {
        try {
            val jsonString = File(filePath).readText()
            val jsonArray = json.parseToJsonElement(jsonString).jsonObject["students"]?.jsonArray
            jsonArray?.forEach { jsonElement ->
                val studentMap = parseJsonToMap(jsonElement)
                val student = Student(studentMap)
                students.add(student)
            }
        } catch (e: Exception) {
            println("Ошибка при чтении файла: ${e.message}")
        }
    }

    private fun parseJsonToMap(jsonElement: JsonElement): HashMap<String, Any?> {
        val map = HashMap<String, Any?>()
        jsonElement.jsonObject.forEach { (key, value) ->
            map[key] = value.jsonPrimitive.content
        }
        return map
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
        students.sortBy { "${it.lastName}${it.firstName}${it.middleName}" }
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
}