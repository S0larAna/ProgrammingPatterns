class StudentList(var studentListStrategy: StudentListStrategy) {
    private var students: MutableList<Student> = mutableListOf()

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }

    fun get_k_n_student_short_list(k: Int, n: Int): List<Student_short> {
        val startIndex = (k - 1) * n
        val endIndex = minOf(startIndex + n, students.size)
        return students.subList(startIndex, endIndex).map { Student_short(it) }
    }

    fun sortByName() {
        students.sortBy { "${it.lastName}${it.firstName}${it.middleName}" }
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun replaceStudent(id: Int, newStudent: Student) {
        val index = students.indexOfFirst { it.id == id }
        if (index != -1) {
            students[index] = newStudent
        }
    }

    fun removeStudent(id: Int) {
        students.removeIf { it.id == id }
    }

    fun get_student_short_count(): Int {
        return students.size
    }

    fun readFromFile(filePath: String){
        students = studentListStrategy.readFromFile(filePath)
    }

    fun writeToFile(filePath: String){
        studentListStrategy.writeToFile(students, filePath)
    }
}