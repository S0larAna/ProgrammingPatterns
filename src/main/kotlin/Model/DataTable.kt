package Model

class Data_table(private val data: Array<Array<Any?>>) {

    fun getRowCount(): Int{
        return data.size
    }

    fun getColumnCount(): Int{
        return if (data.isNotEmpty()) data[0].size else 0
    }

    operator fun get(rowIndex: Int, columnIndex: Int): Any? {
        if (rowIndex < 0 || rowIndex >= getRowCount() || columnIndex < 0 || columnIndex >= getColumnCount()) {
            throw IndexOutOfBoundsException("Invalid index: [$rowIndex, $columnIndex]")
        }
        return data[rowIndex][columnIndex]
    }

    override fun toString(): String {
        return data.joinToString("\n") { row ->
            row.joinToString(", ", "[", "]")
        }
    }
}