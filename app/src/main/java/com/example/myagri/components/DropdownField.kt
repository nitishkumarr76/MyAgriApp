package com.example.myagri.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                readOnly = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme
                        .onBackground.copy(0.7f)
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
                    focusedContainerColor =
                        MaterialTheme.colorScheme.background,

                    unfocusedContainerColor =
                        MaterialTheme.colorScheme.background,

                    focusedBorderColor =
                        MaterialTheme.colorScheme.primary,

                    unfocusedBorderColor =
                        MaterialTheme.colorScheme.outline,

                    focusedTextColor =
                        MaterialTheme.colorScheme.onBackground,

                    unfocusedTextColor =
                        MaterialTheme.colorScheme.onBackground
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
                                color = MaterialTheme.colorScheme
                                    .onBackground.copy(0.8f)
                            )
                        },
                        onClick = {
                            onOptionSelected(it)
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable {
                        onExpandChange()
                    }
            )
        }
    }
}
