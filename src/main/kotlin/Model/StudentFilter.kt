package Model

interface StudentFilter {
    fun filter(students: List<Student>): List<Student>
}

class BaseStudentFilter : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return students
    }
}

class GitHubFilter(private val filter: StudentFilter, val gitSubstring: String?, private val hasGit: String) : StudentFilterDecorator(filter) {
    override fun filter(students: List<Student>): List<Student> {
        val filteredStudents = super.filter(students)
        return if (gitSubstring != null && hasGit == "Да") {
            filteredStudents.filter { it.github != null && it.github!!.contains(gitSubstring) }
        } else if (gitSubstring != null && hasGit == "Нет") {
            filteredStudents.filter { it.github == null }
        } else {
            filteredStudents
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

class EmailFilter(filter: StudentFilter, val emailSubstring: String?, private val hasEmail: String) : StudentFilterDecorator(filter) {
    override fun filter(students: List<Student>): List<Student> {
        val filteredStudents = super.filter(students)
        return if (emailSubstring != null && hasEmail == "Да") {
            filteredStudents.filter { it.email != null && it.email!!.contains(emailSubstring) }
        }
        else if (emailSubstring != null && hasEmail == "Нет") {
            filteredStudents.filter { it.email == null }
        }
        else {
            filteredStudents
        }
    }
}

class TelegramFilter(filter: StudentFilter, val telegramSubstring: String?, private val hasTelegram: String) : StudentFilterDecorator(filter) {
    override fun filter(students: List<Student>): List<Student> {
        val filteredStudents = super.filter(students)
        return if (telegramSubstring != null && hasTelegram == "Да") {
            filteredStudents.filter { it.telegram != null && it.telegram!!.contains(telegramSubstring) }
        }
        else if(telegramSubstring != null && hasTelegram == "Нет") {
            filteredStudents.filter { it.telegram == null }
        }
        else {
            filteredStudents
        }
    }
}

class PhoneFilter(filter:StudentFilter, private val phoneSubstring: String?, private val hasPhone: String) : StudentFilterDecorator(filter) {
    override fun filter(students: List<Student>): List<Student> {
        val filteredStudents = super.filter(students)
        return if (phoneSubstring != null && hasPhone == "Да") {
            filteredStudents.filter { it.phone != null && it.phone!!.contains(phoneSubstring) }
        } else if(phoneSubstring != null && hasPhone == "Нет") {
            filteredStudents.filter { it.phone == null }
        }
        else {
            filteredStudents
        }
    }
}