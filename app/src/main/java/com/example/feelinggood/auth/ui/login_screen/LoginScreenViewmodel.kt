package com.example.feelinggood.auth.ui.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feelinggood.auth.repository.UserAccountRepo
import com.example.feelinggood.auth.user_actions.EventsOnLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userAccountRepo: UserAccountRepo
) : ViewModel() {
    var emailId by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onEvent(userAction: EventsOnLoginScreen) {
        when (userAction) {
            is EventsOnLoginScreen.OnEmailIdClick -> this.emailId = userAction.emailId
            is EventsOnLoginScreen.OnPasswordClick -> this.password = userAction.password
        }
    }


    fun logIn(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            userAccountRepo.signIn(
                userEmail = emailId,
                password = password,
                onSuccess = {
                    onSuccess()
                    resetStates()
                },
                onFailure = {
                    onFailure()
                }
            )
        }
    }

    private fun resetStates() {
        emailId = ""
        password = ""
    }
}