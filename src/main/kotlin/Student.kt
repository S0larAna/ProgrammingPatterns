class Student(
    id: Int,
    lastName: String,
    firstName: String,
    middleName: String,
    phone: String? = null,
    telegram: String? = null,
    email: String? = null,
    github: String? = null
    )
{
    var id: Int = id
        get() = field
        set(value){
            field = value
        }

    var lastName: String = lastName
        get() = field
        set(value){
            field = value
        }

    var firstName: String = firstName
        get() = field
        set(value){
            field = value
        }
    var middleName: String = middleName
        get() = field
        set(value) {
            field = value
        }

    var phone: String? = phone
        get() = field
        set(value) {
            field = value
        }

    var telegram: String? = telegram
        get() = field
        set(value) {
            field = value
        }

    var email: String? = email
        get() = field
        set(value) {
            field = value
        }

    var github: String? = github
        get() = field
        set(value) {
            field = value
        }

    constructor(id: Int, lastName: String, firstName: String, middleName: String) : this(
        id, lastName, firstName, middleName, null, null, null, null
    )

    fun stringify(){
        println("Студент ID: $id")
        println("ФИО: $lastName $firstName $middleName")
        println("Телефон: ${phone ?: "-"}")
        println("Telegram: ${telegram ?: "-"}")
        println("Email: ${email ?: "-"}")
        println("GitHub: ${github ?: "-"}")
    }
}