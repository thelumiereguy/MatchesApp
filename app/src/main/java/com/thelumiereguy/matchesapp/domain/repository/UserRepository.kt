package com.thelumiereguy.matchesapp.domain.repository

import com.thelumiereguy.matchesapp.domain.enitity.UsersList

interface UserRepository {
    suspend fun getUsersList(isInternetConnected: Boolean): UsersList
}