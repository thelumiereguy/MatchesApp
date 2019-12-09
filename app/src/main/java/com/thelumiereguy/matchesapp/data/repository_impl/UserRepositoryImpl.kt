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
            networkService.getAllUsersAsync().await()

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


    /**
     * Maps the list of
     * @code UsersList.User to UserEntity
     *
     *This list of users are then inserted into the db
     *
     * @param usersList The list of users to be inserted
     */
    override suspend fun saveAllUsers(usersList: UsersList) {
        val usersEntityList: MutableList<UsersEntity> = ArrayList()
        usersEntityList.addAll(usersList.results.map { user -> UsersEntity.mapFrom(user) })
        usersDao.insertUsers(usersEntityList)
    }


    /**
     * Updates a certain property of a specific User.
     * Calls the Dao's updateUser() function
     *
     * Used to update status and isFavourite property
     *
     * @param user The specific user whose property has to be changed
     */
    override suspend fun updateUser(user: UsersList.User) {
        usersDao.updateUser(UsersEntity.mapFrom(user))
    }


    /**
     *
     * Clears the whole db
     * Called on Logout
     *
     * Calls the Dao's nukeDB() function
     */
    override suspend fun logoutUser() {
        usersDao.nukeDB()
    }


    /**
     * Get the list of all favourited users ie isFavourite = true
     *
     *  Calls the Dao's getAllFavourites() function
     */
    override suspend fun getFavourites(): UsersList {
        val usersList: List<UsersList.User> = usersDao.getAllFavourites().map {
            it.mapToUser()
        }
        return UsersList(
            UsersList.Info(),
            usersList
        )

    }
}