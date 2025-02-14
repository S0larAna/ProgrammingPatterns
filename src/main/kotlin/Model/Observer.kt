package Model

import javafx.scene.control.Label

interface Observer {
    fun updatePageInfo(currentPage: Int, totalPages: Int)
    fun setTableData(dataTable: List<Student_short>)
}