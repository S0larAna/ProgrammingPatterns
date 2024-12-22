import App.MainApp
import DBConnection.DatabaseManager
import DBConnection.Students_list_DB

fun main() {
    /*val student1 = Student(1, "Иванов", "Иван", "Иванович", phone = "+79123456789", email = "ivan@example.com")
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

    val student4 = Student(lastName = "Иванов", firstName = "Иван", middleName = "Иванович")

    val student5 = Student("lastName:Иванов,firstName:Иван,middleName:Иванович,phone:+79001234567,telegram:@ivanov,email:ivanov@example.com,github:https://github.com/johndoe")
    println(student5)
    val students = StudentsListTxt("src/main/resources/students.txt")
    println("Прочитано ${students.students.size} студентов:")
    students.students.forEach { println(it) }
    Student.writeToTxt("src/main/resources", "students_output.txt", students.students)
    val students_test = Student.readFromTxt("src/main/resources/students_output.txt")
    val shortStudentList = mutableListOf<Student_short>()
    for (student in students_test){
        shortStudentList.add(Student_short(student))
    }
    val datalist = Data_list_student_short(shortStudentList)
    for (item in datalist.getDataList()){
        println(item)
    }
    val datatable = datalist.get_data()
    println(datatable)*/
//    val jsonStrategy = StudentsListYAML()
//    val students = StudentList(jsonStrategy)
//    students.readFromFile("src/main/resources/students.yaml")
//    students.writeToFile("src/main/resources/students_output.yaml")
//    val dbConnection = DatabaseManager
//    dbConnection.connect()
//    val studentDb = Students_list_DB(dbConnection)
//    println(studentDb.getStudentById(1))
    MainWindow.createAndShow()
}