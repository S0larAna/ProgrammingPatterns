package DBConnection

import java.sql.Connection
import java.sql.DriverManager

class DatabaseManager {
    private var connection: Connection? = null

    fun connect() {
        Class.forName("org.sqlite.JDBC")
        connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite")
    }

    fun executeQuery(connection: Connection, query: String) {
        try {
            val rows = connection?.prepareStatement(query)?.executeUpdate()
        } catch (e: java.lang.Exception) {
            println(e)
        }
    }

    fun getStudents(): List<Map<String, Any>> {
        val users = mutableListOf<Map<String, Any>>()
        val sql = "SELECT * FROM students"

        connection?.createStatement()?.executeQuery(sql)?.use { rs ->
            while (rs.next()) {
                users.add(mapOf(
                    "id" to rs.getInt("id"),
                    "last_name" to rs.getString("last_name"),
                    "email" to rs.getString("email")
                ))
            }
        }
        return users
    }

    fun close() {
        connection?.close()
    }
}