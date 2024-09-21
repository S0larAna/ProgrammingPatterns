class Student(
    val id: Int = generateId(),
    var lastName: String,
    var firstName: String,
    var middleName: String,
    var phone: String? = null,
    var telegram: String? = null,
    var email: String? = null,
    var github: String? = null
) {

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

    private fun isValidPhoneNumber(phone: String): Boolean {
        val regex = Regex("^\\+?[0-9]{10,13}\$")
        return regex.matches(phone)
    }

    private fun isValidTelegramHandle(telegram: String): Boolean {
        val regex = Regex("^@[a-zA-Z0-9_]{5,32}\$")
        return regex.matches(telegram)
    }

    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[\\w.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$")
        return regex.matches(email)
    }

    private fun isValidGithubUsername(github: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9-]{1,39}\$")
        return regex.matches(github)
    }

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
        if (!hasGitHub()){
            println("Doesn't have GitHub")
        }
        if (!hasAnyContactinfo()){
            println("Doesn't have any contact information")
        }
    }

    fun setContacts(phone: String? = this.phone, telegram: String? = this.telegram, email: String? = this.email) {
        if (phone != null) this.phone = phone
        if (telegram != null) this.telegram = telegram
        if (email != null) this.email = email
    }

    private fun hasGitHub(): Boolean{
        return (github.isNullOrEmpty())
    }

    private fun hasAnyContactinfo(): Boolean{
        return !phone.isNullOrEmpty() || !telegram.isNullOrEmpty() || !email.isNullOrEmpty()
    }

    companion object {

        private var currentId = 0
        private fun generateId(): Int {
            return ++currentId
        }

        private fun parseDataString(dataString: String): HashMap<String, Any?> {
            val data = HashMap<String, Any?>()
            val pairs = dataString.split(",")

            for (pair in pairs) {
                val (key, value) = pair.split(":").map { it.trim() }
                when (key) {
                    "lastName", "firstName", "middleName" -> data[key] = value
                    "phone", "telegram", "email", "github" -> if (value.isNotEmpty()) data[key] = value
                    else -> throw IllegalArgumentException("Unknown key: $key")
                }
            }

            if (!data.containsKey("lastName") || !data.containsKey("firstName") || !data.containsKey("middleName")) {
                throw IllegalArgumentException("Missing required fields")
            }

            return data
        }
    }
}