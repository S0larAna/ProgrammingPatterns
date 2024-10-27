abstract class Data_list<T : Comparable<T>> {
    protected var data: MutableList<T>
    protected var selected: MutableSet<Int> = mutableSetOf()

    val size: Int
        get() = data.size

    constructor(elements: List<T>) {
        this.data = elements.sorted().toMutableList()
    }

    operator fun get(index: Int): T {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Invalid index: $index")
        }
        return data[index]
    }

    fun getDataList(): MutableList<T> {
        return this.data
    }

    fun indexOf(element: T): Int {
        return data.binarySearch(element)
    }

    fun contains(element: T): Boolean {
        return indexOf(element) >= 0
    }

    override fun toString(): String {
        return data.joinToString(", ", "[", "]")
    }

    fun select(number: Int) {
        if (number < 0 || number >= size) {
            throw IndexOutOfBoundsException("Invalid index: $number")
        }
        selected.add(number)
    }

    fun get_selected(): List<T> {
        return selected.sorted().map { data[it] }
    }

    abstract fun get_names(): Array<Array<String>>

    fun clearSelection() {
        selected.clear()
    }
    fun get_data(): Data_table{
        val names = get_names()
        val rows = get_rows()
        return Data_table(names + rows)
    }
    abstract protected fun get_rows(): Array<Array<String>>
}