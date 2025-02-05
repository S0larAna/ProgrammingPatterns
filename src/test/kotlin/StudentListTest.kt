import Model.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StudentListTest {

    private lateinit var studentList: StudentList
    private lateinit var student1: Student
    private lateinit var student2: Student
    private lateinit var student3: Student

    @BeforeEach
    fun setUp() {
        student1 = Student(1, "Иванов", "Иван", "Иванович", github = "ivanov-github")
        student2 = Student(2, "Петров", "Петр", "Петрович", github = "petrov-github")
        student3 = Student(3, "Сидоров", "Сидор", "Сидорович")
        val strategyMock = StudentListStrategyMock()
        studentList = StudentList(strategyMock)
        studentList.addStudent(student1)
        studentList.addStudent(student2)
        studentList.addStudent(student3)
    }

    @Test
    fun `test addStudent`() {
        val newStudent = Student(4, "Новиков", "Николай", "Николаевич")
        studentList.addStudent(newStudent)
        assertEquals(4, studentList.get_student_short_count())
        assertNotNull(studentList.getStudentById(4))
    }

    @Test
    fun `test removeStudent`() {
        studentList.removeStudent(1)
        assertEquals(2, studentList.get_student_short_count())
        assertNull(studentList.getStudentById(1))
    }

    @Test
    fun `test replaceStudent`() {
        val updatedStudent = Student(1, "Иванов", "Иван", "Иванович", github = "new-github")
        studentList.replaceStudent(1, updatedStudent)
        val student = studentList.getStudentById(1)
        assertNotNull(student)
        assertEquals("new-github", student?.github)
    }

    @Test
    fun `test get_k_n_student_short_list`() {
        val filteredList = studentList.get_k_n_student_short_list(1, 2, true, "github")
        assertEquals(2, filteredList.size)
        assertEquals("ivanov-github", filteredList[0].github)
        assertEquals("petrov-github", filteredList[1].github)
    }

    @Test
    fun `test sortByName`() {
        studentList.sortByName()
        val sortedList = studentList.get_k_n_student_short_list(1, 3, null, null)
        assertEquals("Иванов И. И.", sortedList[0].nameWithInitials)
        assertEquals("Петров П. П.", sortedList[1].nameWithInitials)
        assertEquals("Сидоров С. С.", sortedList[2].nameWithInitials)
    }

    @Test
    fun `test filter students with GitHub`() {
        val filteredList = studentList.get_k_n_student_short_list(1, 3, true, null)
        assertEquals(2, filteredList.size)
        assertEquals("ivanov-github", filteredList[0].github)
        assertEquals("petrov-github", filteredList[1].github)
    }

    @Test
    fun `test filter students with GitHub containing substring`() {
        val filteredList = studentList.get_k_n_student_short_list(1, 3, true, "ivanov")
        assertEquals(1, filteredList.size)
        assertEquals("ivanov-github", filteredList[0].github)
    }
}