data class Student(
    val id: Int,
    var lastName: String,
    var firstName: String,
    var middleName: String,
    var phone: String? = null,
    var telegram: String? = null,
    var email: String? = null,
    var github: String? = null
    )
{
    override fun toString(): String{
        return """
Студент ID: $id
ФИО: $lastName $firstName $middleName
Телефон: ${phone ?: "Не указан"}
Telegram: ${telegram ?: "Не указан"}
Email: ${email ?: "Не указан"}
GitHub: ${github ?: "Не указан"}
        """
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): Student {
            return Student(
                id = map["id"] as? Int ?: throw IllegalArgumentException("ID обязателен"),
                lastName = map["lastName"] as? String ?: throw IllegalArgumentException("Фамилия обязательна"),
                firstName = map["firstName"] as? String ?: throw IllegalArgumentException("Имя обязательно"),
                middleName = map["middleName"] as? String ?: throw IllegalArgumentException("Отчество обязательно"),
                phone = map["phone"] as? String,
                telegram = map["telegram"] as? String,
                email = map["email"] as? String,
                github = map["github"] as? String
            )
        }
    }
}