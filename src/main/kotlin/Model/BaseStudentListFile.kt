package Model

open class BaseStudentListFile : StudentListStrategy {
    protected var students: MutableList<Student> = mutableListOf()

    override fun addStudent(student: Student) {
        students = readFromFile()
        students.add(student)
        writeToFile(students)
    }

    override fun updateStudent(id: Int, student: Student) {
        students = readFromFile()
        val index = students.indexOfFirst { it.id == id }
        if (index != -1) {
            students[index] = student
            writeToFile(students)
        }
    }

    override fun removeStudent(id: Int) {
        students = readFromFile()
        if (students.removeIf { it.id == id }) {
            writeToFile(students)
        }
    }

    override fun writeToFile(students: MutableList<Student>) {
    }

    override fun readFromFile(): MutableList<Student> {
        return students
    }
}