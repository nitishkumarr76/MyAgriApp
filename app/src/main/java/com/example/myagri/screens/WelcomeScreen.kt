package com.example.myagri.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.example.myagri.auth.GoogleAuthHelper
import com.example.myagri.auth.AuthViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myagri.R

@Composable
fun WelcomeScreen(navController: NavController){

    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel() }
    val googleSignInClient = GoogleAuthHelper.getClient(context)

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.result
                isLoading = true

                authViewModel.firebaseAuthWithGoogle(
                    account,
                    onSuccess = {
                        isLoading = false
                        navController.navigate("main") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    },
                    onFailure = {
                        isLoading = false
                        errorMessage = it
                    }
                )

            } catch (e: Exception) {
                isLoading = false
                errorMessage = e.message ?: "Login failed"
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){

        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Welcome to\nFarm Management",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Simplify your farm operations\nand cultivate success",
                color = MaterialTheme.colorScheme.onBackground.copy(0.75f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(modifier = Modifier.fillMaxWidth()){

                Button(
                    onClick = { navController.navigate("mobile") },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Login with Phone")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val signInIntent = googleSignInClient.signInIntent
                        launcher.launch(signInIntent)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_1),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Login with Google")
                }

                // 🔥 Show loading
                if (isLoading) {
                    Spacer(modifier = Modifier.height(12.dp))
                    CircularProgressIndicator()
                }

                // 🔥 Show error
                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(errorMessage, color = Color.Red)
                }
            }
        }
    }
}