class Student(
    val id: Int = generateId(),
    lastName: String,
    firstName: String,
    middleName: String,
    phone: String? = null,
    telegram: String? = null,
    email: String? = null,
    github: String? = null
) {

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
        validate()
    }

    var phone: String? = phone
        get() = field
        set(value) {
            if (value != null && !isValidPhoneNumber(value)) {
                throw IllegalArgumentException("Invalid phone number format")
            }
            field = value
        }

    var telegram: String? = telegram
        get() = field
        set(value) {
            if (value != null && !isValidTelegramHandle(value)) {
                throw IllegalArgumentException("Invalid telegram format")
            }
            field = value
        }

    var email: String? = email
        get() = field
        set(value) {
            if (value != null && !isValidEmail(value)) {
                throw IllegalArgumentException("Invalid email format")
            }
            field = value
        }

    var github: String? = github
    get() = field
    set(value) {
        if (value != null && !isValidGithubUsername(value)) {
            throw IllegalArgumentException("Invalid github format")
        }
        field = value
    }

    var firstName: String? = firstName
        get() = field
        set(value) {
            if (value != null && !isValidName(value)) {
                throw IllegalArgumentException("Invalid name format")
            }
            field = value
        }

    var lastName: String? = lastName
        get() = field
        set(value) {
            if (value != null && !isValidName(value)) {
                throw IllegalArgumentException("Invalid name format")
            }
            field = value
        }

    var middleName: String? = middleName
        get() = field
        set(value) {
            if (value != null && !isValidName(value)) {
                throw IllegalArgumentException("Invalid name format")
            }
            field = value
        }

    constructor(data: HashMap<String, Any?>) : this(
        id = generateId(),
        lastName = data["lastName"] as? String?: throw IllegalArgumentException("Invalid lastName format"),
        firstName = data["firstName"] as? String?: throw IllegalArgumentException("Invalid firstName format"),
        middleName = data["middleName"] as? String?: throw IllegalArgumentException("Illegal middleName"),
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
            val regex = Regex("^https://github.com/[a-zA-Z0-9-]{1,39}\$")
            return regex.matches(github)
        }

        private fun isValidName(name: String): Boolean {
            val regex = Regex("^[А-Я]{1}[а-я]{1,39}\$")
            return regex.matches(name)
        }
    }
}