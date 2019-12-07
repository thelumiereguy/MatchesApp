package com.thelumiereguy.matchesapp.domain.repository

import com.thelumiereguy.matchesapp.domain.enitity.UsersList

interface UserRepository {
    suspend fun getUsersList(isInternetConnected: Boolean): UsersList


    suspend fun getFavourites(): UsersList

    suspend fun saveAllUsers(usersList: UsersList)


    suspend fun updateUser(user: UsersList.User)
}