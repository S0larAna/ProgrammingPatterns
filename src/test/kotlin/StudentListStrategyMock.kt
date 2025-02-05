import Model.Student
import Model.StudentListStrategy

class StudentListStrategyMock : StudentListStrategy {
    private val mockData = mutableListOf<Student>()

    override fun readFromFile(filePath: String): MutableList<Student> {
        return mockData
    }

    override fun writeToFile(students: MutableList<Student>, filePath: String) {
        mockData.clear()
        mockData.addAll(students)
    }
}