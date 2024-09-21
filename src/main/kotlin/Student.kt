class Student(
    val id: Int,
    var lastName: String,
    var firstName: String,
    var middleName: String,
    phone: String? = null,
    telegram: String? = null,
    email: String? = null,
    github: String? = null
) {
    constructor(
        lastName: String,
        firstName: String,
        middleName: String,
        phone: String?=null,
        telegram: String? = null,
        email: String? = null,
        github: String? = null
    ) : this(
        id = generateId(),
        lastName = lastName,
        firstName = firstName,
        middleName = middleName,
        phone = phone,
        telegram = telegram,
        email = email,
        github = github
    )

    var phone: String? = phone
        set(value) {
            if (value != null && !isValidPhoneNumber(value)) {
                throw IllegalArgumentException("Invalid phone format")
            }
            field = value
        }

    var telegram: String? = telegram
        set(value) {
            if (value != null && !isValidTelegramHandle(value)) {
                throw IllegalArgumentException("Invalid Telegram handle format")
            }
            field = value
        }

    var email: String? = email
        set(value) {
            if (value != null && !isValidEmail(value)) {
                throw IllegalArgumentException("Invalid email format")
            }
            field = value
        }

    var github: String? = github
        set(value) {
            if (value != null && !isValidGithubUsername(value)) {
                throw IllegalArgumentException("Invalid GitHub username format")
            }
            field = value
        }

    init {
        if (phone != null && !isValidPhoneNumber(phone)) {
            throw IllegalArgumentException("Invalid phone number format")
        }

        if (telegram != null && !isValidTelegramHandle(telegram)) {
            throw IllegalArgumentException("Invalid Telegram handle format")
        }

        if (email != null && !isValidEmail(email)) {
            throw IllegalArgumentException("Invalid email format")
        }

        if (github != null && !isValidGithubUsername(github)) {
            throw IllegalArgumentException("Invalid GitHub username format")
        }
    }

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
        id = data["id"] as? Int ?: throw IllegalArgumentException("ID is required"),
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
    }
}