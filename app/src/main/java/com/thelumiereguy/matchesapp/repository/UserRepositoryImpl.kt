package com.thelumiereguy.matchesapp.repository

import com.thelumiereguy.matchesapp.UsersList
import com.thelumiereguy.matchesapp.data.NetworkService
import com.thelumiereguy.matchesapp.data.UsersDao

class UserRepositoryImpl constructor(
    private val networkService: NetworkService,
    private val usersDao: UsersDao
) : UserRepository {


    /**
     * Chooses if data should be retrieved locally or remotely
     *
     * @param isInternetConnected flag to switch between the two
     *
     */
    override suspend fun getUsersList(isInternetConnected: Boolean): UsersList {

        return if (isInternetConnected) {
            networkService.getAllUsers().await()

        } else {
            val usersList: List<UsersList.User> = usersDao.getAllUsersLocal().map {
                it.mapToUser()
            }
            UsersList(UsersList.Info(), usersList)
        }
    }
}