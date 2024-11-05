import kotlinx.serialization.*

@Serializable
class Student_short(
    val id: Int,
    val nameWithInitials: String,
    val github: String,
    val contact: String
) : StudentBase(), Comparable<Student_short> {
    constructor(student: Student) : this(
        id = student.id,
        nameWithInitials = student.getNameInfo(),
        github = student.getGithubInfo(),
        contact = student.getContactInfo().split(": ")[1]
    )

    constructor(id: Int, infoString: String) : this(
        id = id,
        nameWithInitials = infoString.split("|")[0].trim(),
        github = infoString.split("|")[1].trim().split(": ")[1],
        contact = infoString.split("|")[2].trim().split(": ")[1]
    )

    fun getInfo(): String {
        return "${nameWithInitials} | ${github} | ${contact}"
    }

    override fun compareTo(other: Student_short): Int {
        return this.nameWithInitials.compareTo(other.nameWithInitials)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student_short) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}