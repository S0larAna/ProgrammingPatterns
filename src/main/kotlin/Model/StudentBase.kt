package Model

open class StudentBase {

    companion object {

        private var currentId = 0

        internal fun generateId(): Int {
            return ++currentId
        }

        internal fun parseDataString(dataString: String): HashMap<String, Any?> {
            val data = HashMap<String, Any?>()
            val pairs = dataString.split(",")

            for (pair in pairs) {
                println(pair)
                val (key, value) = pair.split(":", limit=2).map { it.trim() }
                when (key) {
                    "lastName", "firstName", "middleName" -> data[key] = value
                    "phone", "telegram", "email", "github" -> if (value.isNotEmpty()) data[key] = value
                    else -> throw IllegalArgumentException("Unknown key: $key")
                }
            }

            if (!data.containsKey("lastName") || !data.containsKey("firstName") || !data.containsKey("middleName")) {
                throw IllegalArgumentException("Missing required fields")
            }

            return data
        }

        internal fun parseShortDataString(dataString: String): HashMap<String, Any?> {
            val data = HashMap<String, Any?>()
            val pairs = dataString.split(",")

            for (pair in pairs) {
                println(pair)
                val (key, value) = pair.split(":", limit=2).map { it.trim() }
                when (key) {
                    "lastName", "firstName", "middleName" -> data[key] = value
                    "phone", "telegram", "email", "github" -> if (value.isNotEmpty()) data[key] = value
                    else -> throw IllegalArgumentException("Unknown key: $key")
                }
            }

            if (!data.containsKey("lastName") || !data.containsKey("firstName") || !data.containsKey("middleName")) {
                throw IllegalArgumentException("Missing required fields")
            }

            return data
        }

        fun isValidPhoneNumber(phone: String): Boolean {
            val regex = Regex("^\\+?[0-9]{10,13}\$")
            return regex.matches(phone)
        }

        fun isValidTelegramHandle(telegram: String): Boolean {
            val regex = Regex("^@[a-zA-Z0-9_]{5,32}\$")
            return regex.matches(telegram)
        }

        fun isValidEmail(email: String): Boolean {
            val regex = Regex("^[\\w.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$")
            return regex.matches(email)
        }

        fun isValidGithubUsername(github: String): Boolean {
            val regex = Regex("^[a-zA-Z0-9-]{1,39}\$")
            return regex.matches(github)
        }

        fun isValidName(name: String): Boolean {
            val regex = Regex("^[А-Я]{1}[а-я]{1,39}\$")
            return regex.matches(name)
        }
    }
}