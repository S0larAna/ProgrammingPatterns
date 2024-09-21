class Datalist<Any : Comparable<Any>> private constructor(private val data: MutableList<Any>) {

    val size: Int
        get() = data.size

    constructor(elements: List<Any>) : this(elements.sorted().toMutableList())

}