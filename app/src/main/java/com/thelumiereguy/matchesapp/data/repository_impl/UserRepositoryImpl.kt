package com.thelumiereguy.matchesapp.data.repository_impl

import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.data.network_service.NetworkService
import com.thelumiereguy.matchesapp.data.db.UsersDao
import com.thelumiereguy.matchesapp.data.db.enitity.UsersEntity
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
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
            UsersList(
                UsersList.Info(),
                usersList
            )
        }
    }

    override suspend fun saveAllUsers(usersList: UsersList) {
        val usersEntityList: MutableList<UsersEntity> = ArrayList()
        usersEntityList.addAll(usersList.results.map { user -> UsersEntity.mapFrom(user)})
        usersDao.insertUsers(usersEntityList)
    }
}