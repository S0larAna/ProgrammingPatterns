package DBConnection
import Model.Data_list
import Model.Data_list_student_short
import Model.Student
import Model.StudentListStrategy
import Model.Student_short

class Students_list_DB(private val dbManager: DatabaseManager) {

    fun readStudents():MutableList<Student>{
        val sqlQuery = "SELECT * FROM students"
        var studentList = mutableListOf<Student>()
        dbManager.connection?.createStatement()?.executeQuery(sqlQuery)?.use { rs ->
            while (rs.next()) {
                studentList.add(
                    Student(hashMapOf(
                    "id" to rs.getInt("id"),
                    "lastName" to rs.getString("last_name"),
                    "firstName" to rs.getString("first_name"),
                    "middleName" to rs.getString("middle_name"),
                    "phone" to rs.getString("phone"),
                    "telegram" to rs.getString("telegram"),
                    "email" to rs.getString("email"),
                    "github" to rs.getString("github")
                ))
                )
            }
        }
        return studentList
    }

    fun writeStudents(students: List<Student>){
        for (student in students){
            addStudent(student)
            println(student.toString())
        }
    }

    fun getStudentById(id: Int): Student?{
        val sqlQuery = "SELECT * FROM students where id=$id"
        dbManager.connection?.createStatement()?.executeQuery(sqlQuery)?.use { rs ->
            while (rs.next()) {
                return Student(hashMapOf(
                    "id" to rs.getInt("id"),
                    "lastName" to rs.getString("last_name"),
                    "firstName" to rs.getString("first_name"),
                    "middleName" to rs.getString("middle_name"),
                    "phone" to rs.getString("phone"),
                    "telegram" to rs.getString("telegram"),
                    "email" to rs.getString("email"),
                    "github" to rs.getString("github")
                ))
            }
        }
        return null
    }

    fun addStudent(student: Student) {
        val query = """
        INSERT INTO students
        (last_name, first_name, middle_name, phone, telegram, email, github)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """.trimIndent()

        dbManager.connection?.prepareStatement(query)?.use { statement ->
            statement.setString(1, student.lastName)
            statement.setString(2, student.firstName)
            statement.setString(3, student.middleName)
            statement.setString(4, student.phone)
            statement.setString(5, student.telegram)
            statement.setString(6, student.email)
            statement.setString(7, student.github)
            statement.executeUpdate()
        }
    }

    fun updateStudent(id: Int, student: Student) {
        require(id > 0) { "ID студента должен быть положительным числом" }
        val query = """
        UPDATE students 
            SET last_name = ?, 
                first_name = ?, 
                middle_name = ?, 
                phone = ?, 
                telegram = ?, 
                email = ?, 
                github = ? 
            WHERE id = ?
    """.trimIndent()

        dbManager.connection?.prepareStatement(query)?.use { statement ->
            statement.setString(1, student.lastName)
            statement.setString(2, student.firstName)
            statement.setString(3, student.middleName)
            statement.setString(4, student.phone)
            statement.setString(5, student.telegram)
            statement.setString(6, student.email)
            statement.setString(7, student.github)
            statement.setInt(8, student.id)
            statement.executeUpdate()
        }
    }

    fun deleteStudent(id: Int) {
        require(id > 0) { "ID студента должен быть положительным числом" }
        val query = """DELETE FROM students WHERE id = ?"""

        dbManager.connection?.prepareStatement(query)?.use { statement ->
            statement.setInt(1, id)
            statement.executeUpdate()
        }
    }
}