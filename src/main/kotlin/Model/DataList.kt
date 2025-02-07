package Model

abstract class Data_list<T : Comparable<T>>(val data: List<T>) {

    var selected: MutableSet<Int> = mutableSetOf()

    val size: Int
        get() = data.size

    operator fun get(index: Int): T {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Invalid index: $index")
        }
        return data[index]
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

    fun getSelected(): List<T> {
        return selected.sorted().map { data[it] }
    }

    abstract fun getNames(): Array<Any?>

    fun getData(): Data_table {
        val dataArray:MutableList<Array<Any?>> = mutableListOf()
        dataArray.add(getNames())
        for (el in getRows()){
            dataArray.add(el)
        }
        return Data_table(dataArray.toTypedArray<Array<Any?>>())
    }
    abstract protected fun getRows(): Array<Array<Any?>>
}