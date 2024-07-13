package com.example.feelinggood.auth.user_actions

sealed interface EventsOnRegistrationScreen {
    data class OnNameFieldClick(val name: String) : EventsOnRegistrationScreen
    data class OnEmailIdClick(val emailId: String) : EventsOnRegistrationScreen
    data class OnNewPasswordClick(val newPassword: String) : EventsOnRegistrationScreen
    data class OnConfirmPasswordClick(val confirmPassword: String) : EventsOnRegistrationScreen
}