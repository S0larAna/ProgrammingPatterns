class Student_short(
    val id: Int,
    val nameWithInitials: String,
    val github: String,
    val contact: String
) : StudentBase() {
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

    override fun toString(): String {
        return getInfo()
    }
}