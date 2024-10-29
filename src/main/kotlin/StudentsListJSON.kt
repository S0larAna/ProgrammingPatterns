import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class StudentsListJSON(): StudentListStrategy {

    override fun readFromFile(filePath: String): MutableList<Student>{
        var students = mutableListOf<Student>()
        try {
            val jsonString = File(filePath).readText()
            val jsonOb = Json.parseToJsonElement(jsonString).jsonObject["students"]?.jsonArray
            if (jsonOb != null) {
                jsonOb.forEach {
                    var studentHash = HashMap<String, Any?>()
                    it.jsonObject.entries.forEach {
                        val (key, value) = it.toPair()
                        studentHash[key] = value.toString().replace("\"", "")
                        println(studentHash)
                    }
                    println(studentHash["phone"])
                    students.add(Student(studentHash))
                }
            }
            println(students)

        } catch (e: Exception) {
            println("Error reading file: ${e.message}")
        }
        return students
    }

    override fun writeToFile(students: MutableList<Student>, filePath: String){
        try {
            val json = Json {
                prettyPrint = true
            }
            val file = File(filePath)
            val jsonObject = buildJsonObject {
                putJsonArray("students") {
                    students.forEach { student ->
                        addJsonObject {
                            put("firstName", student.firstName)
                            put("lastName", student.lastName)
                            put("middleName", student.middleName)
                            student.phone?.let { put("phone", it) }
                            student.telegram?.let { put("telegram", it) }
                            student.email?.let { put("email", it) }
                            student.github?.let { put("github", it) }
                            }
                        }
                    }
                }
            file.bufferedWriter().use { writer ->
                writer.write(json.encodeToString(jsonObject))
            }
        }
        catch (e: Exception){
            println("Got error:" + e.message)
        }
    }
}