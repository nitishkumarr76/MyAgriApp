package com.example.myagri

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.myagri.screens.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(){

    val navController = rememberNavController()

    // Auto-login check
    val user = FirebaseAuth.getInstance().currentUser
    val startDestination = if (user != null) "main" else "welcome"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable("welcome") { WelcomeScreen(navController) }
        composable("mobile") { MobileNumberScreen(navController) }

        composable("otp/{phone}") { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            OtpVerificationScreen(navController, phone)
        }

        composable("main") { MainScreen(navController) }
    }
}