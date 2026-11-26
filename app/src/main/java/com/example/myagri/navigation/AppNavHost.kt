package com.example.myagri.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.myagri.features.auth.AuthViewModel
import com.example.myagri.features.auth.MobileNumberScreen
import com.example.myagri.features.auth.OtpVerificationScreen
import com.example.myagri.features.auth.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()

    // Auto-login check
    val user = FirebaseAuth.getInstance().currentUser
    val startDestination = if (user != null) "main" else "welcome"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("welcome") { WelcomeScreen(navController) }
        composable("mobile") {
            MobileNumberScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }

        composable("otp/{phone}") { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone") ?: ""

            OtpVerificationScreen(
                navController = navController,
                phone = phone,
                viewModel = authViewModel
            )
        }

        composable("main") {
            MainScreen(navController)
        }
    }
}