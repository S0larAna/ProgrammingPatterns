import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StudentTest {
    @Test
    fun `create student with only required fields`() {
        val student = Student(1, "Иванов", "Иван", "Иванович")
        assertEquals(1, student.id)
        assertEquals("Иванов", student.lastName)
        assertEquals("Иван", student.firstName)
        assertEquals("Иванович", student.middleName)
        assertNull(student.phone)
        assertNull(student.telegram)
        assertNull(student.email)
        assertNull(student.github)
    }

    @Test
    fun `create student with all fields correctly filled`() {
        val student = Student(
            1, "Иванов", "Иван", "Иванович",
            phone = "+79123456789",
            telegram = "@ivanov",
            email = "ivanov@example.com",
            github = "ivanov-github"
        )
        assertEquals(1, student.id)
        assertEquals("Иванов", student.lastName)
        assertEquals("Иван", student.firstName)
        assertEquals("Иванович", student.middleName)
        assertEquals("+79123456789", student.phone)
        assertEquals("@ivanov", student.telegram)
        assertEquals("ivanov@example.com", student.email)
        assertEquals("ivanov-github", student.github)
    }

    @Test
    fun `create student with incorrect phone number`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "Иванов", "Иван", "Иванович", phone = "123")
        }
    }

    @Test
    fun `create student without id`() {
        assertThrows<IllegalArgumentException> {
            Student.fromMap(mapOf(
                "lastName" to "Иванов",
                "firstName" to "Иван",
                "middleName" to "Иванович"
            ))
        }
    }

    @Test
    fun `create student without firstName`() {
        assertThrows<IllegalArgumentException> {
            Student.fromMap(mapOf(
                "id" to 1,
                "lastName" to "Иванов",
                "middleName" to "Иванович"
            ))
        }
    }

    @Test
    fun `create student without lastName`() {
        assertThrows<IllegalArgumentException> {
            Student.fromMap(mapOf(
                "id" to 1,
                "firstName" to "Иван",
                "middleName" to "Иванович"
            ))
        }
    }

    @Test
    fun `create student without middleName`() {
        assertThrows<IllegalArgumentException> {
            Student.fromMap(mapOf(
                "id" to 1,
                "lastName" to "Иванов",
                "firstName" to "Иван"
            ))
        }
    }

    @Test
    fun `create student with null optional fields`() {
        val student = Student(1, "Иванов", "Иван", "Иванович",
            phone = null, telegram = null, email = null, github = null)
        assertEquals(1, student.id)
        assertEquals("Иванов", student.lastName)
        assertEquals("Иван", student.firstName)
        assertEquals("Иванович", student.middleName)
        assertNull(student.phone)
        assertNull(student.telegram)
        assertNull(student.email)
        assertNull(student.github)
    }

    @Test
    fun `modify phone number to valid one`() {
        val student = Student(1, "Иванов", "Иван", "Иванович")
        student.phone = "+79123456789"
        assertEquals("+79123456789", student.phone)
    }

    @Test
    fun `modify phone number to invalid one`() {
        val student = Student(1, "Иванов", "Иван", "Иванович")
        assertThrows<IllegalArgumentException> {
            student.phone = "123"
        }
    }
}