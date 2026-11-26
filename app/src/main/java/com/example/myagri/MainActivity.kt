package com.example.myagri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myagri.navigation.AppNavHost
import com.example.myagri.ui.theme.MyAgriTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAgriTheme {
                AppNavHost()
            }
        }
    }
}
