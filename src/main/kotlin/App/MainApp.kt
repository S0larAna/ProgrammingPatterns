package App

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*

class MainApp {
    @Composable
    fun App() {
        System.setProperty("file.encoding", "windows-1251")
        var selectedTab by remember { mutableStateOf(0) }
        val tabs = listOf("Students", "Tab 2", "Tab 3")

        MaterialTheme {
            Column {
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }

                when (selectedTab) {
                    0 -> StudentListView().studentListShow()
                    1 -> Text("Tab 2")
                    2 -> Text("Tab 3")
                }
            }
        }
    }
}