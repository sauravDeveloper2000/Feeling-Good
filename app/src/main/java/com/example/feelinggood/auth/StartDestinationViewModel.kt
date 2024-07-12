package com.example.feelinggood.auth

import androidx.lifecycle.ViewModel
import com.example.feelinggood.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StartDestinationViewModel @Inject constructor() : ViewModel() {
    var _startDestination = MutableStateFlow<Destination>(Destination.PreAuth)
        private set

    fun definesStartDestination(
        destination: Destination
    ) {
        _startDestination.value = destination
    }
}