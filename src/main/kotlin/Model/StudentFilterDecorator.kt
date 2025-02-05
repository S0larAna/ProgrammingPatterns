package Model

class StudentFilterDecorator(private val filters: List<StudentFilter>) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        var filteredStudents = students
        for (filter in filters) {
            filteredStudents = filter.filter(filteredStudents)
        }
        return filteredStudents
    }
}