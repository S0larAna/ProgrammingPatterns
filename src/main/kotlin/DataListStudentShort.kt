class Data_list_student_short(students: List<Student_short>) : Data_list<Student_short>(students) {

    override fun getNames(): Array<Any?> {
        return arrayOf(arrayOf("id","name with initials", "github", "contact"))
    }

    override fun getRows(): Array<Array<Any?>> {
        val rows = Array(data.size) { index ->
            val student = data[index]
            arrayOf<Any?>(
                student.id,
                student.nameWithInitials,
                student.github,
                student.contact
            )
        }
        return rows
    }
}