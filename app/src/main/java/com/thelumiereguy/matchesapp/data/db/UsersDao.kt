package com.thelumiereguy.matchesapp.data.db

import androidx.room.*
import com.thelumiereguy.matchesapp.data.db.enitity.UsersEntity


@Dao
interface UsersDao {


    /**
     * Inserts list of Users into the DB
     *
     * @param user List of users to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user: List<UsersEntity>)



    /**
     * Updates the properties of a certain user such as Status or Favourite
     *
     * @param user The user whose data needs to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UsersEntity)


    /**
     * Get the list of all the records in the database
     */
    @Query("SELECT * from UsersEntity")
    suspend fun getAllUsersLocal(): List<UsersEntity>

    /**
     * Get the list of all favourited users ie isFavourite = true
     */
    @Query("SELECT * from UsersEntity WHERE favourite = 1")
    suspend fun getAllFavourites(): List<UsersEntity>


    /**
     * Clears the whole db
     *
     * Called on Logout
     */
    @Query("DELETE FROM UsersEntity")
    fun nukeDB()
}