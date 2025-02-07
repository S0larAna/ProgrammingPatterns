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

    fun get_k_n_student_short_list(k: Int, n: Int, gitSubstring: String?, nameSubstring: String?, emailSubstring: String?, telegramSubstring: String?, phoneSubstring: String?): List<Student_short> {
        val filters = mutableListOf<StudentFilter>()
        if (gitSubstring != null) filters.add(GitHubFilter(gitSubstring))
        if (nameSubstring != null) filters.add(NameFilter(nameSubstring))
        if (emailSubstring != null) filters.add(EmailFilter(emailSubstring))
        if (telegramSubstring != null) filters.add(TelegramFilter(telegramSubstring))
        if (phoneSubstring != null) filters.add(PhoneFilter(phoneSubstring))

        val filterDecorator = StudentFilterDecorator(filters)
        val filteredList = filterDecorator.filter(students)

        val startIndex = (k - 1) * n
        val endIndex = minOf(startIndex + n, filteredList.size)
        return filteredList.subList(startIndex, endIndex).map { Student_short(it) }
    }

    fun sortByName() {
        students.sortBy { "${it.lastName}${it.firstName}${it.middleName}" }
    }

    fun addStudent(student: Student) {
        students.add(student)
        //TODO фиксануть костыль
        writeToFile(mutableListOf(student))
        println(student.toString())
        notifyObservers()
    }

    fun replaceStudent(id: Int, newStudent: Student) {
        val index = students.indexOfFirst { it.id == id }
        if (index != -1) {
            students[index] = newStudent
            notifyObservers()
        }
    }

    fun removeStudent(id: Int) {
        if (students.removeIf { it.id == id }) {
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
        strategy.writeToFile(student, )
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