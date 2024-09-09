fun main() {
    val student1 = Student(1, "Иванов", "Иван", "Иванович", phone = "+79123456789", email = "ivan@example.com")
    val student2 = Student(2, "Ивановв", "Иван", "Иванович", phone = "+79123456789")
    val studentData = mapOf(
        "id" to 3,
        "lastName" to "Сидоров",
        "firstName" to "Алексей",
        "middleName" to "Владимирович",
        "telegram" to "@alexsid",
        "github" to "alexsid"
    )
    val student3 = Student.fromMap(studentData)

    println("Информация о студентах:")
    println(student1.toString())
    println(student2.toString())
    println(student3.toString())

    student2.phone = "+79876543210"
    student2.email = "example@example.com"
}