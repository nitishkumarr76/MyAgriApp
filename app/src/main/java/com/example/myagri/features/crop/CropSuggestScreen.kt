package com.example.myagri.features.crop

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myagri.R
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropRecommendationScreen(
    innerNavController: NavController,
    viewModel: CropViewModel = viewModel()
) {

    val state = viewModel.uiState

    val soilTypes = listOf(
        "Loamy Soil", "Sandy Soil", "Clay Soil"
    )

    val seasons = listOf(
        "Kharif", "Rabi", "Zaid"
    )

    val locations = listOf(
        "Punjab, India",
        "Haryana, India",
        "UP, India"
    )

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
                    IconButton(
                        onClick = {
                            innerNavController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
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
                shape = RoundedCornerShape(16.dp)
            ) {

                Column {

                    Image(
                        painter = painterResource(
                            id = R.drawable.img_4
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        DropdownField(
                            label = "Soil Type",
                            selectedValue = state.selectedSoil,
                            expanded = soilExpanded,
                            onExpandChange = {
                                soilExpanded = !soilExpanded
                            },
                            options = soilTypes,
                            onOptionSelected = {
                                viewModel.selectSoil(it)
                                soilExpanded = false
                            }
                        )

                        Spacer(Modifier.height(12.dp))

                        DropdownField(
                            label = "Season",
                            selectedValue = state.selectedSeason,
                            expanded = seasonExpanded,
                            onExpandChange = {
                                seasonExpanded = !seasonExpanded
                            },
                            options = seasons,
                            onOptionSelected = {
                                viewModel.selectSeason(it)
                                seasonExpanded = false
                            }
                        )

                        Spacer(Modifier.height(12.dp))

                        DropdownField(
                            label = "Location",
                            selectedValue = state.selectedLocation,
                            expanded = locationExpanded,
                            onExpandChange = {
                                locationExpanded = !locationExpanded
                            },
                            options = locations,
                            onOptionSelected = {
                                viewModel.selectLocation(it)
                                locationExpanded = false
                            }
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = {
                                viewModel.getSuggestion()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Get Suggestion")
                        }

                        state.error?.let {
                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (state.suggestions.isNotEmpty()) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Suggested Crops",
                            fontWeight = FontWeight.SemiBold
                        )

                        state.suggestions.forEach { crop ->

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = "🌾 ${crop.name}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = crop.reason,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
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