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
    override suspend fun signIn(
        userEmail: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: () -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener { task ->
                try {
                    val firebaseUser = task.result.user
                    onSuccess(firebaseUser)
                } catch (e: Exception) {
                    onFailure()
                }
            }.addOnCanceledListener {
                onFailure()
            }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

}