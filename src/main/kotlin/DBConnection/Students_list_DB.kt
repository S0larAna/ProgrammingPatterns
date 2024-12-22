package DBConnection
import Data_list
import Data_list_student_short
import Student
import StudentListStrategy
import Student_short
import java.sql.SQLException
import java.sql.Statement

class Students_list_DB(private val dbManager: DatabaseManager) {

    fun readStudents():MutableList<Student>{
        val sqlQuery = "SELECT * FROM students"
        var studentList = mutableListOf<Student>()
        dbManager.connection?.createStatement()?.executeQuery(sqlQuery)?.use { rs ->
            while (rs.next()) {
                studentList.add(Student(hashMapOf(
                    "id" to rs.getInt("id"),
                    "lastName" to rs.getString("last_name"),
                    "firstName" to rs.getString("first_name"),
                    "middleName" to rs.getString("middle_name"),
                    "phone" to rs.getString("phone"),
                    "telegram" to rs.getString("telegram"),
                    "email" to rs.getString("email"),
                    "github" to rs.getString("github")
                )))
            }
        }
        return studentList
    }

    fun writeStudents(students: List<Student>){
        for (student in students){
            addStudent(student)
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

    fun get_k_n_student_short_list(k: Int, n: Int): Data_list<Student_short>{
        val sqlQuery = "SELECT * FROM students"
        var studentList = mutableListOf<Student>()
        dbManager.connection?.createStatement()?.executeQuery(sqlQuery)?.use { rs ->
            while (rs.next()) {
                studentList.add(Student(hashMapOf(
                    "id" to rs.getInt("id"),
                    "lastName" to rs.getString("last_name"),
                    "firstName" to rs.getString("first_name"),
                    "middleName" to rs.getString("middle_name"),
                    "phone" to rs.getString("phone"),
                    "telegram" to rs.getString("telegram"),
                    "email" to rs.getString("email"),
                    "github" to rs.getString("github")
                )))
            }
        }
        val startIndex = (k - 1) * n
        val endIndex = minOf(startIndex + n, studentList.size)
        val studentShortList = studentList.subList(startIndex, endIndex).map { Student_short(it) }
        return Data_list_student_short(studentShortList)
    }

    fun addStudent(student: Student): Int {
        dbManager.connection.use { connection ->
            val query = """
                INSERT INTO students 
                (last_name, first_name, middle_name, phone, telegram, email, github) 
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()

            dbManager.connection?.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use { statement ->
                if (statement != null) {
                    statement.setString(1, student.lastName)
                    statement.setString(2, student.firstName)
                    statement.setString(3, student.middleName)
                    statement.setString(4, student.phone)
                    statement.setString(5, student.telegram)
                    statement.setString(6, student.email)
                    statement.setString(7, student.github)
                    statement.executeUpdate()

                    val generatedKeys = statement.generatedKeys
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1)
                    }
                }
            }
        }
        throw SQLException("Не удалось получить сгенерированный ID")
    }

    fun updateStudent(id: Int, student: Student) {
        require(id > 0) { "ID студента должен быть положительным числом" }

        dbManager.connection?.use { connection ->
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

            connection.prepareStatement(query).use { statement ->
                statement.setString(1, student.lastName)
                statement.setString(2, student.firstName)
                statement.setString(3, student.middleName)
                statement.setString(4, student.phone)
                statement.setString(5, student.telegram)
                statement.setString(6, student.email)
                statement.setString(7, student.github)
                statement.setInt(8, student.id)

                val updatedRows = statement.executeUpdate()
            }
        }
    }

    fun deleteStudent(id: Int) {
        require(id > 0) { "ID студента должен быть положительным числом" }

        dbManager.connection?.use { connection ->
            val query = "DELETE FROM students WHERE id = ?"

            connection.prepareStatement(query).use { statement ->
                statement.setInt(1, id)

                val deletedRows = statement.executeUpdate()
            }
        }
    }

    fun getStudentsCount(): Int {
        dbManager.connection?.use { connection ->
            val query = "SELECT COUNT(*) as count FROM students"

            connection.createStatement().use { statement ->
                val resultSet = statement.executeQuery(query)
                if (resultSet.next()) {
                    return resultSet.getInt("count")
                }
            }
        }
        return 0
    }
}