package com.example.myagri.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myagri.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropRecommendationScreen(innerNavController: NavController) {

    val soilTypes = listOf("Loamy Soil", "Sandy Soil", "Clay Soil")
    val seasons = listOf("Kharif", "Rabi", "Zaid")
    val locations = listOf("Punjab, India", "Haryana, India", "UP, India")

    var selectedSoil by remember { mutableStateOf(" Select soil type") }
    var selectedSeason by remember { mutableStateOf("Select season") }
    var selectedLocation by remember { mutableStateOf(" Select location") }

    var soilExpanded by remember { mutableStateOf(false) }
    var seasonExpanded by remember { mutableStateOf(false) }
    var locationExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Crop Suggestion",
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
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_4),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    DropdownField(
                        label = "Soil Type",
                        selectedValue = selectedSoil,
                        expanded = soilExpanded,
                        onExpandChange = { soilExpanded = !soilExpanded },
                        options = soilTypes,
                        onOptionSelected = {
                            selectedSoil = it
                            soilExpanded = false
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownField(
                        label = "Season",
                        selectedValue = selectedSeason,
                        expanded = seasonExpanded,
                        onExpandChange = { seasonExpanded = !seasonExpanded },
                        options = seasons,
                        onOptionSelected = {
                            selectedSeason = it
                            seasonExpanded = false
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownField(
                        label = "Location",
                        selectedValue = selectedLocation,
                        expanded = locationExpanded,
                        onExpandChange = { locationExpanded = !locationExpanded },
                        options = locations,
                        onOptionSelected = {
                            selectedLocation = it
                            locationExpanded = false
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Get Suggestion", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Suggested Crops",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            "🌾 Rice",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            "High yield in this season",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ricesq),
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownField(
    label: String,
    selectedValue: String,
    expanded: Boolean,
    onExpandChange: () -> Unit,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {

    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                readOnly = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                ),
                trailingIcon = {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,

                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,

                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onExpandChange,
                containerColor = MaterialTheme.colorScheme.background
            ) {
                options.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                            )
                        },
                        onClick = { onOptionSelected(it) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable { onExpandChange() }
            )
        }
    }
}