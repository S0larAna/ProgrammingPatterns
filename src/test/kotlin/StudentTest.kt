import Model.Student
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
    fun `create student from a string`() {
        val student = Student("id:1,lastName:Иванов,firstName:Иван,middleName:Иванович,phone:+79001234567,telegram:@ivanov,email:ivanov@example.com,github:johndoe")
        assertEquals(1, student.id)
        assertEquals("Иванов", student.lastName)
        assertEquals("Иван", student.firstName)
        assertEquals("Иванович", student.middleName)
        assertEquals("+79001234567", student.phone)
        assertEquals("@ivanov", student.telegram)
        assertEquals("ivanov@example.com", student.email)
        assertEquals("johndoe", student.github)
    }

    @Test
    fun `create student with incorrect phone number`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "Иванов", "Иван", "Иванович", phone = "123")
        }
    }

    @Test
    fun `create student without firstName`() {
        assertThrows<IllegalArgumentException> {
            Student(hashMapOf(
                "id" to 1,
                "lastName" to "Иванов",
                "middleName" to "Иванович"
            ))
        }
    }

    @Test
    fun `create student without last name`() {
        assertThrows<IllegalArgumentException> {
            Student(hashMapOf(
                "id" to 1,
                "firstName" to "Иван",
                "middleName" to "Иванович"
            ))
        }
    }

    @Test
    fun `create student without middle name`() {
        assertThrows<IllegalArgumentException> {
            Student(hashMapOf(
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
        student.phone = "+1234567890"
        assertEquals("+1234567890", student.phone)
    }

    @Test
    fun `modify phone number to invalid one`() {
        val student = Student(1, "Иванов", "Иван", "Иванович")
        assertThrows<IllegalArgumentException> {
            student.phone = "123"
        }
    }

    @Test
    fun `test invalid telegram handle`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "Иванов", "Иван", "Иванович", telegram = "invalid_handle")
        }
    }

    @Test
    fun `test invalid email`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "Иванов", "Иван", "Иванович", email = "invalid_email")
        }
    }

    @Test
    fun `test invalid github username`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "Иванов", "Иван", "Иванович", github = "invalid-username-!")
        }
    }

    @Test
    fun `test invalid first name`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "Иванов", "123", "Иванович")
        }
    }

    @Test
    fun `test invalid last name`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "123", "Иван", "Иванович")
        }
    }

    @Test
    fun `test invalid middle name`() {
        assertThrows<IllegalArgumentException> {
            Student(1, "Иванов", "Иван", "123")
        }
    }

    @Test
    fun `test setContacts method`() {
        val student = Student(1, "Иванов", "Иван", "Иванович")
        student.setContacts(phone = "+79123456789", telegram = "@ivanov", email = "ivanov@example.com")
        assertEquals("+79123456789", student.phone)
        assertEquals("@ivanov", student.telegram)
        assertEquals("ivanov@example.com", student.email)
    }

    @Test
    fun `test getNameInfo method`() {
        val student = Student(1, "Иванов", "Иван", "Иванович")
        assertEquals("Иванов И. И.", student.getNameInfo())
    }

    @Test
    fun `test getGithubInfo method`() {
        val student = Student(1, "Иваанов", "Иван", "Иванович", github = "ivanov-github")
        assertEquals("ivanov-github", student.getGithubInfo())
    }

    @Test
    fun `test getContactInfo method with phone`() {
        val student = Student(1, "Иванов", "Иван", "Иванович", phone = "+79123456789")
        assertEquals("Телефон: +79123456789", student.getContactInfo())
    }

    @Test
    fun `test getContactInfo method with telegram`() {
        val student = Student(1, "Иванов", "Иван", "Иванович", telegram = "@ivanov")
        assertEquals("Telegram: @ivanov", student.getContactInfo())
    }

    @Test
    fun `test getContactInfo method with email`() {
        val student = Student(1, "Иванов", "Иван", "Иванович", email = "ivanov@example.com")
        assertEquals("Email: ivanov@example.com", student.getContactInfo())
    }

    @Test
    fun `test getContactInfo method with no contact info`() {
        val student = Student(1, "Иванов", "Иван", "Иванович")
        assertEquals("Контакты: не указаны", student.getContactInfo())
    }

    @Test
    fun `test toFileString method`() {
        val student = Student(1, "Иванов", "Иван", "Иванович", phone = "+79123456789", telegram = "@ivanov", email = "ivanov@example.com", github = "ivanov-github")
        assertEquals("lastName:Иванов,firstName:Иван,middleName:Иванович,phone:+79123456789,telegram:@ivanov,email:ivanov@example.com,github:ivanov-github", student.toFileString())
    }
}