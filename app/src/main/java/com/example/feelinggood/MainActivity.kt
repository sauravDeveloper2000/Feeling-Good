package com.example.feelinggood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.feelinggood.auth.ui.login_screen.LoginScreen
import com.example.feelinggood.ui.theme.FeelingGoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeelingGoodTheme {
                LoginScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}