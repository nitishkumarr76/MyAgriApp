package com.example.myagri.features.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketPriceScreen(innerNavController: NavController) {

    var selectedTab by remember { mutableStateOf("Crops") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Market Prices",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        innerNavController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Punjab, India",
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs (Crops / Vegetables)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(50)
                    )
                    .padding(4.dp)
            ) {

                TabButton(
                    title = "Crops",
                    selected = selectedTab == "Crops"
                ) { selectedTab = "Crops" }

                TabButton(
                    title = "Vegetables",
                    selected = selectedTab == "Vegetables"
                ) { selectedTab = "Vegetables" }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Crop", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Price (₹/Quintal)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Trend", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Data List
            LazyColumn {

                val data = listOf(
                    MarketItem("Wheat", "₹ 2,150", "+2.5%"),
                    MarketItem("Rice", "₹ 2,300", "+1.8%"),
                    MarketItem("Maize", "₹ 1,900", "-0.5%"),
                    MarketItem("Cotton", "₹ 6,200", "+3.2%"),
                    MarketItem("Mustard", "₹ 5,250", "+1.1%"),
                    MarketItem("Soybean", "₹ 4,000", "-0.8%")
                )

                items(data.size) { index ->
                    MarketRow(data[index])
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("View Full Report")
            }
        }
    }
}

@Composable
fun RowScope.TabButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primary
            else
                Color.Transparent,
            contentColor = if (selected)
                Color.White
            else
                MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(title, fontSize = 14.sp)
    }
}

// 🔹 Data Class
data class MarketItem(
    val name: String,
    val price: String,
    val trend: String
)

@Composable
fun MarketRow(item: MarketItem) {

    val trendColor = if (item.trend.contains("+")) Color(0xFF2E7D32) else Color.Red

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = item.price,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = item.trend,
                color = trendColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}