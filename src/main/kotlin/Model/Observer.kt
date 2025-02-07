package Model

interface Observer {
    fun wholeEntitiesCount()
    fun setTableData(dataTable: List<Student_short>)
}