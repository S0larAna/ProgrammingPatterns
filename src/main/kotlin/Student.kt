class Student(
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
    fun stringify(){
        println("Студент ID: $id")
        println("ФИО: $lastName $firstName $middleName")
        println("Телефон: ${phone ?: "-"}")
        println("Telegram: ${telegram ?: "-"}")
        println("Email: ${email ?: "-"}")
        println("GitHub: ${github ?: "-"}")
    }
}