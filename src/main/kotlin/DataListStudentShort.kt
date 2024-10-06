class Data_list_student_short(students: List<Student_short>) : Data_list<Student_short>(students) {

    override fun get_names(): List<String> {
        return data.map { it.nameWithInitials }
    }

    override fun get_rows(): Array<Array<String>> {
        val rows = Array(data.size) { index ->
            val student = data[index]
            arrayOf(
                student.id.toString(),
                student.nameWithInitials,
                student.github,
                student.contact
            )
        }
        return rows
    }
}