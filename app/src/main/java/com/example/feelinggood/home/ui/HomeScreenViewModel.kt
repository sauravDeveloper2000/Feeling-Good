package com.example.feelinggood.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feelinggood.auth.repository.UserAccountRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userAccountRepo: UserAccountRepo
) : ViewModel() {
    fun signOut() {
        viewModelScope.launch {
            userAccountRepo.signOut()
        }
    }
}