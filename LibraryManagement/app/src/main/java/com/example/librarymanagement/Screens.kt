package com.example.librarymanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

data class Student(val id: Int, val name: String, val borrowedBooks: List<Book>)
data class Book(val id: Int, val name: String)

val books = mutableStateListOf(
    Book(1, "Thất lạc cõi người"),
    Book(2, "Tà Dương"),
    Book(3, "Nữ Sinh"),
    Book(4, "Kaoru Hana Wa Rin To Saku"),
    Book(5, "Blue Box"),
    Book(6, "Lovely Complex"),
    Book(7, "Masamune no revenge"),
    Book(8, "Nisekoi"),
    Book(9, "Wotakoi"),
    Book(10, "Love is War")
)

val students = List(10) { index ->
    val randomBooks = books.shuffled().take(Random.nextInt(0, 5))
    Student(
        id = index + 1,
        name = listOf(
            "Rin",
            "Isagi",
            "Kaiser",
            "Sae",
            "Nagi",
            "Yume",
            "Arya",
            "Violet",
            "Aki",
            "Kaoruko"
        )[index],
        borrowedBooks = randomBooks
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementScreen(navController: NavController) {
    var selectedStudent by remember { mutableStateOf(students.first()) }
    var showStudentDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hệ thống \nQuản lý Thư viện", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Sinh viên", fontWeight = FontWeight.SemiBold)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = selectedStudent.name,
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    readOnly = true
                )
                Button(onClick = { showStudentDialog = true }, colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))) {
                    Text("Thay đổi")
                }
            }

            Text("Danh sách sách", fontWeight = FontWeight.SemiBold)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFECECEC), shape = MaterialTheme.shapes.medium)
                    .padding(8.dp)
            ){
                if (selectedStudent.borrowedBooks.isEmpty()) {
                    Text(
                        "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }else{
                    LazyColumn(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(selectedStudent.borrowedBooks) { book ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = true,
                                        onCheckedChange = { }
                                    )
                                    Text(book.name)
                                }
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
            )
            {
                Text("Thêm")
            }
        }
    }
    if (showStudentDialog) {
        AlertDialog(
            onDismissRequest = { showStudentDialog = false },
            title = { Text("Chọn sinh viên") },
            text = {
                Column {
                    students.forEach { student ->
                        TextButton(
                            onClick = {
                                selectedStudent = student
                                showStudentDialog = false
                            }
                        ) {
                            Text(student.name)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Danh sách sinh viên") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(students) { student ->
                Student(student)
            }
        }
    }
}

@Composable
fun Student(student: Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = student.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color(0xFF1E1E1E)
            )
            Spacer(Modifier.height(8.dp))

            if (student.borrowedBooks.isEmpty()) {
                Text("Chưa mượn quyển sách nào.")
            } else {
                Column {
                    student.borrowedBooks.forEachIndexed { idx, book ->
                        Text("${idx + 1}. ${book.name}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfBook(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sách hiện có") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(books, key = { it.id }) { book ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(book.name, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}


