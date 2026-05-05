package com.example.myagri.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.*

@Composable
fun MainScreen(parentNavController: NavController){

    val innerNavController = rememberNavController()

    NavHost(
        navController = innerNavController,
        startDestination = "bottom"
    ){
        composable("bottom") { BottomNavScreen(innerNavController) }
        composable("home") { HomeScreen(innerNavController) }
        composable("chat") { ChatScreen(innerNavController) }
        composable("history") { HistoryScreen(innerNavController) }
        composable("shop") { ShopScreen(innerNavController) }
        composable("profile") { ProfileScreen(innerNavController, parentNavController) }
        composable("crop") { CropRecommendationScreen(innerNavController) }
        composable("disease") { DiseaseDetectionScreen(innerNavController) }
        composable("result") { ResultScreen(innerNavController) }
        composable("weather") { WeatherForecastScreen(innerNavController) }
        composable("market") { MarketPriceScreen(innerNavController) }
    }
}