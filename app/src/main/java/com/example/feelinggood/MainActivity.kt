package com.example.feelinggood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.feelinggood.auth.StartDestinationViewModel
import com.example.feelinggood.core.navigation.AppNavigation
import com.example.feelinggood.core.navigation.Destination
import com.example.feelinggood.ui.theme.FeelingGoodTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val startDestinationViewModel: StartDestinationViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
        setContent {
            FeelingGoodTheme {
                AppNavigation(startDestinationViewModel = startDestinationViewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
            } else {
                startDestinationViewModel.definesStartDestination(destination = Destination.PreAuth)
            }
        }
    }
}