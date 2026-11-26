package com.example.myagri.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.myagri.features.chat.ChatScreen
import com.example.myagri.features.crop.CropRecommendationScreen
import com.example.myagri.features.diseases.DiseaseDetectionScreen
import com.example.myagri.features.history.HistoryScreen
import com.example.myagri.features.home.HomeScreen
import com.example.myagri.features.market.MarketPriceScreen
import com.example.myagri.features.profile.ProfileScreen
import com.example.myagri.features.diseases.ResultScreen
import com.example.myagri.features.shop.ShopScreen
import com.example.myagri.features.weather.WeatherForecastScreen

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