class Student(
    val id: Int,
    var lastName: String,
    var firstName: String,
    var middleName: String,
    phone: String? = null,
    var telegram: String? = null,
    var email: String? = null,
    var github: String? = null
) {
    var phone: String? = phone
        set(value) {
            isValidPhoneNumber(value)
            field = value
        }

    init {
        isValidPhoneNumber(phone)
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

    companion object {

        fun isValidPhoneNumber(phone: String?) {
            val phoneRegex = Regex("^[+]?[0-9-]{10,15}$")
            if (phone != null && !phoneRegex.matches(phone)) {
                throw IllegalArgumentException("Invalid phone number format")
            }
        }

        fun fromMap(map: Map<String, Any?>): Student {
            return Student(
                id = map["id"] as? Int ?: throw IllegalArgumentException("id is required"),
                lastName = map["lastName"] as? String ?: throw IllegalArgumentException("lastName is required"),
                firstName = map["firstName"] as? String ?: throw IllegalArgumentException("firstName is required"),
                middleName = map["middleName"] as? String ?: throw IllegalArgumentException("middleName is required"),
                phone = map["phone"] as? String,
                telegram = map["telegram"] as? String,
                email = map["email"] as? String,
                github = map["github"] as? String
            )
        }
    }
}