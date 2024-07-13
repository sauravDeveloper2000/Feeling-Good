package com.example.feelinggood.auth.ui.registration_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.feelinggood.auth.user_actions.EventsOnRegistrationScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor() : ViewModel() {

    var name by mutableStateOf("")
        private set
    var emailId by mutableStateOf("")
        private set
    var newPassword by mutableStateOf("")
        private set
    var confirmNewPassword by mutableStateOf("")
        private set

    fun onEvent(userAction: EventsOnRegistrationScreen) {
        when (userAction) {
            is EventsOnRegistrationScreen.OnConfirmPasswordClick -> this.confirmNewPassword =
                userAction.confirmPassword

            is EventsOnRegistrationScreen.OnEmailIdClick -> this.emailId = userAction.emailId
            is EventsOnRegistrationScreen.OnNameFieldClick -> this.name = userAction.name
            is EventsOnRegistrationScreen.OnNewPasswordClick -> this.newPassword =
                userAction.newPassword
        }
    }
}