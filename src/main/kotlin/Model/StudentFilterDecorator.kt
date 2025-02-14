package Model

open class StudentFilterDecorator(private val studentFilter: StudentFilter) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return studentFilter.filter(students)
    }
}