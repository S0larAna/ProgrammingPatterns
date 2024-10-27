class Data_list_student_short(students: List<Student_short>) : Data_list<Student_short>(students) {

    override fun get_names(): Array<Array<String>> {
        return arrayOf(arrayOf("id","name with initials", "github", "contact"))
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