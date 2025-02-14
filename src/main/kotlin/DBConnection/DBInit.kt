package DBConnection

import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {
    var connection: Connection? = null

    fun connect() {
        Class.forName("org.sqlite.JDBC")
        connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite")
    }

    fun close() {
        connection?.close()
    }
}