package Model

interface StudentFilter {
    fun filter(students: List<Student>): List<Student>
}

class BaseStudentFilter : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return students
    }
}

class ContactsFilter(filter: StudentFilter, val fieldName: String, private val fieldSubstring:String?, private val hasField: FilterOption) : StudentFilterDecorator(filter) {
    override fun filter(students: List<Student>): List<Student> {
        val filteredStudents = super.filter(students)
        return when (hasField) {
            FilterOption.YES -> filteredStudents.filter { it.getPropertyValue(fieldName)?.toString()?.toString()?.contains(fieldSubstring ?: "") == true }
            FilterOption.NO -> filteredStudents.filter { it.getPropertyValue(fieldName) == null }
            FilterOption.NO_MATTER -> filteredStudents
        }
    }
}

class NameFilter(
    filter: StudentFilter,
    private val nameSubstring: String?
) : StudentFilterDecorator(filter) {
    override fun filter(students: List<Student>): List<Student> {
        val filteredStudents = super.filter(students)
        return if (nameSubstring != null) {
            filteredStudents.filter { it.firstName!!.contains(nameSubstring) || it.lastName!!.contains(nameSubstring) || it.middleName!!.contains(nameSubstring) }
        } else {
            filteredStudents
        }
    }
}
