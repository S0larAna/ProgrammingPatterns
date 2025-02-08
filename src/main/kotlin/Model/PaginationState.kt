package Model

import View.StudentListView

data class PaginationState(
    var currentPage: Int = 1,
    var totalPages: Int = 1,
    var itemsPerPage: Int = 6
){
    fun updateTotalPages(students: Int){
        totalPages = Math.ceil(students / itemsPerPage.toDouble()).toInt()
    }

    fun updatePageInfo(observer: StudentListView){
        observer.updatePageInfo(currentPage, totalPages)
    }
}
