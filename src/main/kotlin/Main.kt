fun main() {
    val student1 = Student(1, "Иванов", "Иван", "Иванович", phone = "+79123456789", email = "ivan@example.com")
    val student2 = Student(2, "Ивановв", "Иван", "Иванович")

    println("Информация о студентах:")
    student1.stringify()
    student2.stringify()

    student2.phone = "+79876543210"
    student2.email = "example@example.com"
}