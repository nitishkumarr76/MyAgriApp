package com.example.myagri.features.auth

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myagri.R

@Composable
fun MobileNumberScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val context = LocalContext.current
    val activity = context as Activity

    var phoneNumber by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.img_2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Enter Your Phone Number",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("+91", fontWeight = FontWeight.Bold)
                
                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { ch -> ch.isDigit() }) {
                            phoneNumber = it
                        }
                    },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("0000000000") },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    if (phoneNumber.length == 10 && !loading) {

                        loading = true
                        val fullPhone = "+91$phoneNumber"

                        viewModel.sendOtp(
                            phone = fullPhone,
                            activity = activity,
                            onCodeSent = {
                                loading = false
                                navController.navigate("otp/$phoneNumber")
                            },
                            onError = {
                                loading = false
                                Log.e("OTP", it)
                            }
                        )
                    }
                },
                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = if (loading) "Sending..." else "Send OTP",
                    fontSize = 16.sp
                )
            }
        }
    }
}