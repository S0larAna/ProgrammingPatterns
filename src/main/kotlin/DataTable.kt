class Data_table(private val data: Array<Array<String>>) {

    val rowCount: Int
        get() = data.size

    val columnCount: Int
        get() = if (data.isNotEmpty()) data[0].size else 0

    operator fun get(rowIndex: Int, columnIndex: Int): Any? {
        if (rowIndex < 0 || rowIndex >= rowCount || columnIndex < 0 || columnIndex >= columnCount) {
            throw IndexOutOfBoundsException("Invalid index: [$rowIndex, $columnIndex]")
        }
        return data[rowIndex][columnIndex]
    }

    fun getRow(rowIndex: Int): List<Any?> {
        if (rowIndex < 0 || rowIndex >= rowCount) {
            throw IndexOutOfBoundsException("Invalid row index: $rowIndex")
        }
        return data[rowIndex].toList()
    }

    fun getColumn(columnIndex: Int): List<Any?> {
        if (columnIndex < 0 || columnIndex >= columnCount) {
            throw IndexOutOfBoundsException("Invalid column index: $columnIndex")
        }
        return data.map { it[columnIndex] }
    }

    override fun toString(): String {
        return data.joinToString("\n") { row ->
            row.joinToString(", ", "[", "]")
        }
    }
}