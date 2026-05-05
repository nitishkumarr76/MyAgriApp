package com.example.myagri.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(innerNavController: NavController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.6f),
                drawerShape = RectangleShape,
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationDrawerItem(
                    onClick = {
                        scope.launch { drawerState.close() }
                        innerNavController.navigate("profile")
                    },
                    label = {Text(
                        "Profile",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )},
                    selected = false,
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp).clickable{
                        scope.launch { drawerState.open() }
                    }
                )

                Icon(Icons.Default.Notifications, contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Hello, Nitish 👋",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Good morning!",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Weather Card
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                        RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)) {
                        Text("28°C", fontSize = 36.sp, fontWeight = FontWeight.SemiBold)
                        Text("Sunny", fontWeight = FontWeight.SemiBold)
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Column {
                        Text("Location")
                        Text("Punjab, India", fontWeight = FontWeight.SemiBold)
                        Text("Humidity: 45%")
                        Text("Wind: 12 km/h")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick Access
            Text(
                text = "Quick Access",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                QuickItem(
                    title = "Crop\nSuggest",
                    icon = Icons.Default.Grass,
                    modifier = Modifier.weight(1f)
                ) { innerNavController.navigate("crop") }

                QuickItem(
                    title = "Disease\nDetection",
                    icon = Icons.Default.BugReport,
                    modifier = Modifier.weight(1f)
                ) { innerNavController.navigate("disease") }

                QuickItem(
                    title = "Weather\nForecast",
                    icon = Icons.Default.WbSunny,
                    modifier = Modifier.weight(1f)
                ) { innerNavController.navigate("weather") }

                QuickItem(
                    title = "Market\nPrices",
                    icon = Icons.Default.BarChart,
                    modifier = Modifier.weight(1f)
                ) { innerNavController.navigate("market") }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Tip Card
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Farming Tip \uD83C\uDF3F",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Irrigation in the morning reduces\nwater loss and improves crop yield.",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun QuickItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() }
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}