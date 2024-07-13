package com.example.feelinggood.auth.user_actions

sealed interface EventsOnLoginScreen {
    data class OnPasswordClick(val password: String) : EventsOnLoginScreen
    data class OnEmailIdClick(val emailId: String) : EventsOnLoginScreen
}