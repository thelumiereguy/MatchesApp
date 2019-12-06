package com.thelumiereguy.matchesapp.repository

import com.thelumiereguy.matchesapp.UsersList

interface UserRepository {
    suspend fun getUsersList(isInternetConnected: Boolean): UsersList
}