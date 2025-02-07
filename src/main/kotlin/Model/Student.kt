package Model

class Student(
    val id: Int,
    lastName: String,
    firstName: String,
    middleName: String,
    phone: String? = null,
    telegram: String? = null,
    email: String? = null,
    github: String? = null
): StudentBase(), Comparable<Student> {

    init {
        if (!isValidName(lastName) || !isValidName(firstName) || !isValidName(middleName)) {
            throw IllegalArgumentException("Invalid name format")
        }

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
        id = data["id"] as? Int?: generateId(),
        lastName = data["lastName"] as? String?: throw IllegalArgumentException("Invalid lastName format"),
        firstName = data["firstName"] as? String?: throw IllegalArgumentException("Invalid firstName format"),
        middleName = data["middleName"] as? String?: throw IllegalArgumentException("Illegal middleName"),
        phone = data["phone"] as? String,
        telegram = data["telegram"] as? String,
        email = data["email"] as? String,
        github = data["github"] as? String
    )

    constructor(dataString: String) : this(parseDataString(dataString))

    override fun compareTo(other: Student): Int {
        return this.id.compareTo(other.id)
    }

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
        val initials = "${firstName?.first()}. ${middleName?.first()}."
        return "$lastName $initials"
    }

    fun getGithubInfo(): String {
        return github?.let { it } ?: "не указан"
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
        return "id:$id,"+
                "lastName:$lastName," +
                "firstName:$firstName," +
                "middleName:$middleName," +
                "phone:${phone ?: ""}," +
                "telegram:${telegram ?: ""}," +
                "email:${email ?: ""}," +
                "github:${github ?: ""}"
    }
}