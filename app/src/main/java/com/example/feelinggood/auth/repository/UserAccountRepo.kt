package com.example.feelinggood.auth.repository

import com.google.firebase.auth.FirebaseUser

interface UserAccountRepo {
    suspend fun createAccountWithEmailAndPassword(
        userEmail: String,
        userPassword: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun signIn(
        userEmail: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: () -> Unit
    )

    suspend fun signOut()
}