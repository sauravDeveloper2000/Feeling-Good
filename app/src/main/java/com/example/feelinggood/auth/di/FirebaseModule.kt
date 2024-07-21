package com.example.feelinggood.auth.di

import com.example.feelinggood.auth.repository.UserAccountRepo
import com.example.feelinggood.auth.repository.UserAccountRepoImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    /**
     *  Firebase Auth
     */
    @Provides
    @Singleton
    fun providesFirebaseAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    /**
     *  User Account Repository
     */
    @Provides
    @Singleton
    fun providesUserAccountRepo(firebaseAuth: FirebaseAuth): UserAccountRepo =
        UserAccountRepoImpl(firebaseAuth = firebaseAuth)
}