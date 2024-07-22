package com.example.feelinggood.auth.ui.registration_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feelinggood.auth.repository.UserAccountRepo
import com.example.feelinggood.auth.user_actions.EventsOnRegistrationScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val userAccountRepo: UserAccountRepo
) : ViewModel() {

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

    /**
     * Creates new account on firebase server or system with email and password.
     */
    fun createAccount(
        successCase: () -> Unit,
        failureCase: (String) -> Unit
    ) {
        viewModelScope.launch {
            userAccountRepo.createAccountWithEmailAndPassword(
                userEmail = emailId,
                userPassword = newPassword,
                onSuccess = {
                    it?.let { Log.d("Firebase Auth", "User Account Details: $it") }
                    successCase()
                    resetStates()
                },
                onFailure = {
                    Log.d("Firebase Auth", "Failure Cause: $it")
                    failureCase(it)
                }
            )
        }
    }

    private fun resetStates() {
        emailId = ""
        name = ""
        newPassword = ""
        confirmNewPassword = ""
    }
}