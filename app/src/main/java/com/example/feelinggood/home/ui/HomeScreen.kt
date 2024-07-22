package com.example.feelinggood.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Home Screen")
        Button(onClick = { homeScreenViewModel.signOut() }) {
            Text(text = "Sign-Out")
        }
    }
}