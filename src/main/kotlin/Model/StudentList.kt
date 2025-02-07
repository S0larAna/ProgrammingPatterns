package Model

class StudentList(private var strategy: StudentListStrategy) : Subject {
    private var students: MutableList<Student> = mutableListOf()
    private val observers: MutableList<Observer> = mutableListOf()

    fun setStrategy(newStrategy: StudentListStrategy) {
        strategy = newStrategy
    }

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }

    fun get_k_n_student_short_list(
        k: Int,
        n: Int,
        filterSubstrings: MutableMap<String, String?>,
        filterValues: MutableMap<String, String?>): List<Student_short> {
        var filter: StudentFilter = BaseStudentFilter()
        if (filterValues["hasGit"] != "Не важно") {
            filter = GitHubFilter(filter, filterSubstrings["gitSubstring"], filterValues["hasGit"]!!)
        }
        filter = NameFilter(filter, filterSubstrings["initialsSubstring"])
        if (filterValues["hasEmail"] != "Не важно") {
            filter = EmailFilter(filter, filterSubstrings["emailSubstring"], filterValues["hasEmail"]!!)
        }
        if (filterValues["hasTelegram"] != "Не важно") {
            filter = TelegramFilter(filter, filterSubstrings["telegramSubstring"], filterValues["hasTelegram"]!!)
        }
        if (filterValues["hasPhone"] != "Не важно") {
            filter = PhoneFilter(filter, filterSubstrings["phoneSubstring"], filterValues["hasPhone"]!!)
        }
        students = filter.filter(students).toMutableList()

        val startIndex = (k - 1) * n
        val endIndex = minOf(startIndex + n, students.size)
        return students.subList(startIndex, endIndex).map { Student_short(it) }
    }

    fun sortByName() {
        students.sortBy { "${it.lastName}${it.firstName}${it.middleName}" }
    }

    fun addStudent(student: Student) {
        students.add(student)
        strategy.addStudent(student)
        println(student.toString())
        notifyObservers()
    }

    fun replaceStudent(id: Int, newStudent: Student) {
        val index = students.indexOfFirst { it.id == id }
        if (index != -1) {
            students[index] = newStudent
            strategy.updateStudent(id, newStudent)
            notifyObservers()
        }
    }

    fun removeStudent(id: Int) {
        if (students.removeIf { it.id == id }) {
            strategy.removeStudent(id)
            notifyObservers()
        }
    }

    fun get_student_short_count(): Int {
        return students.size
    }

    fun readFromFile(){
        students = strategy.readFromFile()
        notifyObservers()
    }

    fun writeToFile(student: MutableList<Student>){
        strategy.writeToFile(student)
    }

    override fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        for (observer in observers) {
            observer.wholeEntitiesCount()
            observer.setTableData(students.map { Student_short(it) })
        }
    }
}