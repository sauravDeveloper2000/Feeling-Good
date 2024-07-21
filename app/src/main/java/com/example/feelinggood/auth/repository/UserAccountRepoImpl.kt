package com.example.feelinggood.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserAccountRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserAccountRepo {

    override suspend fun createAccountWithEmailAndPassword(
        userEmail: String,
        userPassword: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result.user
                    onSuccess(firebaseUser)
                } else {
                    onFailure(task.exception.toString())
                }
            }
    }

}