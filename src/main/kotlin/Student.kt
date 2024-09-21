import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class Student(
    val id: Int = generateId(),
    var lastName: String,
    var firstName: String,
    var middleName: String,
    var phone: String? = null,
    var telegram: String? = null,
    var email: String? = null,
    var github: String? = null
):StudentBase() {

    init {
        if (phone != null && !isValidPhoneNumber(phone!!)) {
            throw IllegalArgumentException("Invalid phone number format")
        }

        if (telegram != null && !isValidTelegramHandle(telegram!!)) {
            throw IllegalArgumentException("Invalid Telegram handle format")
        }

        if (email != null && !isValidEmail(email!!)) {
            throw IllegalArgumentException("Invalid email format")
        }

        if (github != null && !isValidGithubUsername(github!!)) {
            throw IllegalArgumentException("Invalid GitHub username format")
        }
        validate()
    }

    constructor(dataString: String) : this(parseDataString(dataString))

    constructor(data: HashMap<String, Any?>) : this(
        id = generateId(),
        lastName = data["lastName"] as? String ?: "",
        firstName = data["firstName"] as? String ?: "",
        middleName = data["middleName"] as? String ?: "",
        phone = data["phone"] as? String,
        telegram = data["telegram"] as? String,
        email = data["email"] as? String,
        github = data["github"] as? String
    )

    override fun toString(): String {
        return """
            Студент ID: $id
            ФИО: $lastName $firstName $middleName
            Телефон: ${phone ?: "Не указан"}
            Telegram: ${telegram ?: "Не указан"}
            Email: ${email ?: "Не указан"}
            GitHub: ${github ?: "Не указан"}
        """.trimIndent()
    }

    fun validate() {
        if (!hasGitHub()) {
            println("Doesn't have GitHub")
        }
        if (!hasAnyContactinfo()) {
            println("Doesn't have any contact information")
        }
    }

    fun setContacts(phone: String? = this.phone, telegram: String? = this.telegram, email: String? = this.email) {
        if (phone != null) this.phone = phone
        if (telegram != null) this.telegram = telegram
        if (email != null) this.email = email
    }

    private fun hasGitHub(): Boolean {
        return (github.isNullOrEmpty())
    }

    private fun hasAnyContactinfo(): Boolean {
        return !phone.isNullOrEmpty() || !telegram.isNullOrEmpty() || !email.isNullOrEmpty()
    }


    fun getNameInfo(): String {
        val initials = "${firstName.first()}. ${middleName.first()}."
        return "$lastName $initials"
    }

    fun getGithubInfo(): String {
        return github?.let { "GitHub: $it" } ?: "GitHub: не указан"
    }

    fun getContactInfo(): String {
        return when {
            phone != null -> "Телефон: $phone"
            telegram != null -> "Telegram: $telegram"
            email != null -> "Email: $email"
            else -> "Контакты: не указаны"
        }
    }

    fun toFileString(): String {
        return "lastName: $lastName, " +
                "firstName: $firstName, " +
                "middleName: $middleName, " +
                "phone: ${phone ?: ""}, " +
                "telegram: ${telegram ?: ""}, " +
                "email: ${email ?: ""}, " +
                "github: ${github ?: ""}"
    }

    companion object {
        fun writeToTxt(directoryPath: String, fileName: String, students: List<Student>) {
            val directory = File(directoryPath)
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw IOException("Failed to create directory: $directoryPath")
                }
            }

            if (!directory.isDirectory) {
                throw IllegalArgumentException("The provided path is not a directory: $directoryPath")
            }

            val file = File(directory, fileName)

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

        fun readFromText(filePath: String): List<Student> {
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
                println("Файл не найден: ${e.message}")
            } catch (e: IllegalArgumentException) {
                println("Ошибка при чтении файла: ${e.message}")
            }
            return students
        }
    }
}