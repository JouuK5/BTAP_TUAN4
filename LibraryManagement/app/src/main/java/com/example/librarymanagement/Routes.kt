package com.example.librarymanagement

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class Destination(val label: String){
    @Serializable data object Management: Destination("Quản lý")
    @Serializable data object ListOfBook: Destination("DS Sách")
    @Serializable data object Student: Destination("Sinh viên")
    @Serializable data object SelectedBook : Destination("Chọn sách")
}

sealed class BottomNavigation(val label: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val badgeCount: Int, val route: Destination){
    data object Management : BottomNavigation("Quản lý", Icons.Filled.Home, Icons.Outlined.Home, 0 ,
        Destination.Management)
    data object ListOfBook : BottomNavigation("DS Sách", Icons.Filled.EventNote, Icons.Outlined.EventNote, 0 ,
        Destination.ListOfBook)
    data object Student : BottomNavigation("Sinh viên", Icons.Filled.PersonPin, Icons.Outlined.PersonPin, 0 ,
        Destination.Student)
}