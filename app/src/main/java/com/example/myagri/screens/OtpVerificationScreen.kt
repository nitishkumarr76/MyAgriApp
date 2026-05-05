package com.example.myagri.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.input.key.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.myagri.R
import kotlinx.coroutines.delay

@Composable
fun OtpVerificationScreen(navController: NavController, phone: String) {

    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = List(6) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var timeLeft by remember { mutableStateOf(30) }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }

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

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = "Verify Your Number",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("We’ve sent a 6-digit OTP to")

            Text("+91 $phone", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Enter OTP",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                otpValues.forEachIndexed { index, value ->

                    OutlinedTextField(
                        value = value,
                        onValueChange = { input ->
                            if (input.length <= 1 && input.all { it.isDigit() }) {
                                otpValues[index] = input

                                if (input.isNotEmpty()) {
                                    if (index < 5)
                                        focusRequesters[index + 1].requestFocus()
                                    else
                                        focusManager.clearFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .height(60.dp)
                            .focusRequester(focusRequesters[index])
                            .onKeyEvent {
                                if (it.type == KeyEventType.KeyDown && it.key == Key.Backspace) {
                                    if (otpValues[index].isEmpty() && index > 0) {
                                        otpValues[index - 1] = ""
                                        focusRequesters[index - 1].requestFocus()
                                    } else {
                                        otpValues[index] = ""
                                    }
                                    true
                                } else false
                            },
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Didn't receive the code?", color = Color.Gray)

            Text(
                text = if (timeLeft > 0)
                    "Resend in 00:${timeLeft.toString().padStart(2, '0')}"
                else "Resend OTP",
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    val otp = otpValues.joinToString("")
                    if (otp.length == 6) {
                        navController.navigate("main")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Verify & Login",
                    fontSize = 16.sp
                )
            }
        }
    }
}