package Model

interface StudentFilter {
    fun filter(students: List<Student>): List<Student>
}

class GitHubFilter(private val gitSubstring: String?) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (gitSubstring != null) {
            students.filter { it.github != null && it.github!!.contains(gitSubstring) }
        } else {
            students.filter { it.github != null }
        }
    }
}

class NameFilter(private val nameSubstring: String?) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (nameSubstring != null) {
            students.filter { it.firstName!!.contains(nameSubstring) || it.lastName!!.contains(nameSubstring) || it.middleName!!.contains(nameSubstring) }
        } else {
            students
        }
    }
}

class EmailFilter(private val emailSubstring: String?) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (emailSubstring != null) {
            students.filter { it.email != null && it.email!!.contains(emailSubstring) }
        } else {
            students
        }
    }
}

class TelegramFilter(private val telegramSubstring: String?) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (telegramSubstring != null) {
            students.filter { it.telegram != null && it.telegram!!.contains(telegramSubstring) }
        } else {
            students
        }
    }
}

class PhoneFilter(private val phoneSubstring: String?) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (phoneSubstring != null) {
            students.filter { it.telegram != null && it.telegram!!.contains(phoneSubstring) }
        } else {
            students
        }
    }
}