package Model

interface StudentFilter {
    fun filter(students: List<Student>): List<Student>
}

class GitHubFilter(private val gitSubstring: String?, private val hasGit: String) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (gitSubstring != null && hasGit == "Да") {
            students.filter { it.github != null && it.github!!.contains(gitSubstring) }
        }
        else if (gitSubstring != null && hasGit == "Нет") {
            students.filter { it.github == null }
        }
        else {
            students
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

class EmailFilter(private val emailSubstring: String?, private val hasEmail: String) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (emailSubstring != null && hasEmail == "Да") {
            students.filter { it.email != null && it.email!!.contains(emailSubstring) }
        } else {
            students
        }
    }
}

class TelegramFilter(private val telegramSubstring: String?, private val hasTelegram: String) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (telegramSubstring != null && hasTelegram == "Да") {
            students.filter { it.telegram != null && it.telegram!!.contains(telegramSubstring) }
        } else {
            students
        }
    }
}

class PhoneFilter(private val phoneSubstring: String?, private val hasPhone: String) : StudentFilter {
    override fun filter(students: List<Student>): List<Student> {
        return if (phoneSubstring != null && hasPhone == "Да") {
            students.filter { it.phone != null && it.phone!!.contains(phoneSubstring) }
        } else {
            students
        }
    }
}