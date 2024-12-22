package App

import Student
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember as remember1

class StudentListView {

    @Composable
    fun studentListShow() {
        val students = remember1 {
            mutableStateListOf(
                Student(lastName = "Ivanov", firstName = "Ivan", middleName = "Ivanovich"),
                Student(lastName = "Ivanov", firstName = "Ivan", middleName = "Ivanovich")
            )
        }
        var selectedRows by remember1 { mutableStateOf(setOf<Int>()) }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Область фильтрации
            FilterSection()

            // Таблица студентов
            StudentTable(
            )

        }
    }

    @Composable
    fun CompactFilterOption(
        title: String,
        modifier: Modifier = Modifier
    ) {
        var selectedOption by remember1 { mutableStateOf("Not important") }
        var searchText by remember1 { mutableStateOf("") }

        Card(
            modifier = modifier.fillMaxWidth(),
            elevation = 1.dp
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CompactRadioButton("Yes", selectedOption) { selectedOption = it }
                    CompactRadioButton("No", selectedOption) { selectedOption = it }
                    CompactRadioButton("N/I", selectedOption) { selectedOption = it }
                }

                // Text field that's enabled only when "Yes" is selected
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search $title") },
                    enabled = selectedOption == "Yes",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.caption
                )
            }
        }
    }

    @Composable
    fun FilterSection() {
        var nameText by remember1 { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = nameText,
                onValueChange = { nameText = it },
                label = { Text("Last Name and Initials") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CompactFilterOption("Git")
                    CompactFilterOption("Email")
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CompactFilterOption("Phone")
                    CompactFilterOption("Telegram")
                }
            }
        }
    }

    @Composable
    fun CompactRadioButton(
        text: String,
        selectedOption: String,
        onSelect: (String) -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(2.dp)  // Minimal padding
        ) {
            RadioButton(
                selected = selectedOption == text,
                onClick = { onSelect(text) },
                modifier = Modifier.size(32.dp)  // Smaller radio buttons
            )
            Text(
                text,
                style = MaterialTheme.typography.caption,  // Smaller text
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }

    @Composable
    fun StudentTable() {
        var selectedRows by remember1 { mutableStateOf(setOf<Int>()) }
        var currentPage by remember1 { mutableStateOf(0) }
        var sortedBy by remember1 { mutableStateOf("id") }
        var isAscending by remember1 { mutableStateOf(true) }

        val students = remember1 {
            mutableStateListOf(
                Student(1, "Ivanov", "Ivan", "Ivanovich", "+79123456789", "@johndoe", "ivan@example.com", "johndoe"),
                Student(2, "Petrov", "Petr", "Petrovich", "+79234567890", "@johndoe", "ivan@example.com", "johndoe"),
                Student(3, "Sidorov", "Sidor", "Sidorovich", "+79345678901", "@johndoe", "ivan@example.com", "johndoe"),
                Student(4, "Andreev", "Andrey", "Andreevich", "+79456789012", "@johndoe", "ivan@example.com", "johndoe")
            )
        }

        val itemsPerPage = 2
        val totalPages = (students.size + itemsPerPage - 1) / itemsPerPage

        Column {
            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF7B1FA2))
                    .padding(16.dp)
            ) {
                listOf("ID", "Last Name", "First Name", "Middle Name", "Phone", "Email", "Git").forEach { header ->
                    Text(
                        text = header,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                if (sortedBy == header.lowercase()) {
                                    isAscending = !isAscending
                                } else {
                                    sortedBy = header.lowercase()
                                    isAscending = true
                                }
                            }
                    )
                }
            }

            // Table Content
            LazyColumn {
                val sortedStudents = when (sortedBy) {
                    "id" -> if (isAscending) students.sortedBy { it.id } else students.sortedByDescending { it.id }
                    "last name" -> if (isAscending) students.sortedBy { it.lastName } else students.sortedByDescending { it.lastName }
                    "first name" -> if (isAscending) students.sortedBy { it.firstName } else students.sortedByDescending { it.firstName }
                    "middle name" -> if (isAscending) students.sortedBy { it.middleName } else students.sortedByDescending { it.middleName }
                    "phone" -> if (isAscending) students.sortedBy { it.phone } else students.sortedByDescending { it.phone }
                    "email" -> if (isAscending) students.sortedBy { it.email } else students.sortedByDescending { it.email }
                    "git" -> if (isAscending) students.sortedBy { it.github } else students.sortedByDescending { it.github }
                    else -> students
                }

                val pageStudents = sortedStudents
                    .drop(currentPage * itemsPerPage)
                    .take(itemsPerPage)

                items(pageStudents.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedRows = if (selectedRows.contains(pageStudents[index].id)) {
                        selectedRows - pageStudents[index].id
                    } else {
                        selectedRows + pageStudents[index].id
                    } }
                            .background(if (selectedRows.contains(pageStudents[index].id))
                                Color.LightGray else Color.Transparent)
                            .padding(16.dp)
                    ){
                        Text(pageStudents[index].id.toString(), modifier = Modifier.weight(1f))
                        pageStudents[index].lastName?.let { it1 -> Text(it1, modifier = Modifier.weight(1f)) }
                        pageStudents[index].firstName?.let { it1 -> Text(it1, modifier = Modifier.weight(1f)) }
                        pageStudents[index].middleName?.let { it1 -> Text(it1, modifier = Modifier.weight(1f)) }
                        pageStudents[index].phone?.let { it1 -> Text(it1, modifier = Modifier.weight(1f)) }
                        pageStudents[index].email?.let { it1 -> Text(it1, modifier = Modifier.weight(1f)) }
                        pageStudents[index].github?.let { it1 -> Text(it1, modifier = Modifier.weight(1f)) }
                    }
                }
            }

            // Pagination
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { currentPage = (currentPage - 1).coerceAtLeast(0) },
                    enabled = currentPage > 0,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B1FA2))
                ) {
                    Text("Previous", color = Color.White)
                }

                Text(
                    "Page ${currentPage + 1} of $totalPages",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.body1
                )

                Button(
                    onClick = { currentPage = (currentPage + 1).coerceAtMost(totalPages - 1) },
                    enabled = currentPage < totalPages - 1,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B1FA2))
                ) {
                    Text("Next", color = Color.White)
                }
            }

            ControlSection(
                selectedCount = selectedRows.size,
                onAdd = { /* Add logic */ },
                onEdit = { /* Edit logic */ },
                onDelete = { /* Delete logic */ },
                onRefresh = { /* Refresh logic */ }
            )
        }
    }

    @Composable
    fun ControlSection(
        selectedCount: Int,
        onAdd: () -> Unit,
        onEdit: () -> Unit,
        onDelete: () -> Unit,
        onRefresh: () -> Unit
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onAdd,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B1FA2))
            ) {
                Text("Add", color = Color.White)
            }

            Button(
                onClick = onEdit,
                enabled = selectedCount == 1,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B1FA2))
            ) {
                Text("Edit", color = Color.White)
            }

            Button(
                onClick = onDelete,
                enabled = selectedCount > 0,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B1FA2))
            ) {
                Text("Delete", color = Color.White)
            }

            Button(
                onClick = onRefresh,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B1FA2))
            ) {
                Text("Refresh", color = Color.White)
            }
        }
    }
}