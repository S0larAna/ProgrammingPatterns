fun main() {
    val student1 = Student(1, "Иванов", "Иван", "Иванович", phone = "+79123456789", email = "ivan@example.com")
    val student2 = Student(2, "Ивановв", "Иван", "Иванович", phone = "+79123456789")
    val data: HashMap<String, Any?> = hashMapOf(
        "id" to 3,
        "lastName" to "Doe",
        "firstName" to "John",
        "middleName" to "A.",
        "phone" to "+1234567890",
        "telegram" to "@johndoe",
        "email" to "john.doe@example.com",
        "github" to "https://github.com/johndoe"
    )
    val student3 = Student(data)

    println("Информация о студентах:")
    println(student1.toString())
    println(student2.toString())
    println(student3.toString())

    student2.phone = "+123456"
    student2.firstName = "Имя"
    println(student2.toString())

    val student4 = Student(lastName = "Иванов", firstName = "Иван", middleName = "Иванович")
}