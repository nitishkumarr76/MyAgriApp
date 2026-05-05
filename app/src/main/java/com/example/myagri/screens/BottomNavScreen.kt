package com.example.myagri.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomNavScreen(innerNavController: NavController) {

    var selectedTab by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    selected = selectedTab == "home",
                    onClick = { selectedTab = "home" },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") },
                    colors = navItemColors()
                )

                NavigationBarItem(
                    selected = selectedTab == "chat",
                    onClick = { selectedTab = "chat" },
                    icon = { Icon(Icons.Default.Chat, contentDescription = null) },
                    label = { Text("Chat") },
                    colors = navItemColors()
                )

                NavigationBarItem(
                    selected = selectedTab == "history",
                    onClick = { selectedTab = "history" },
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    label = { Text("History") },
                    colors = navItemColors()
                )

                NavigationBarItem(
                    selected = selectedTab == "shop",
                    onClick = { selectedTab = "shop" },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                    label = { Text("Shop") },
                    colors = navItemColors()
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTab) {
                "home" -> HomeScreen(innerNavController)
                "chat" -> ChatScreen(innerNavController)
                "history" -> HistoryScreen(innerNavController)
                "shop" -> ShopScreen(innerNavController)
            }
        }
    }
}

@Composable
fun navItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = MaterialTheme.colorScheme.primary,
    selectedTextColor = MaterialTheme.colorScheme.primary,
    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
)