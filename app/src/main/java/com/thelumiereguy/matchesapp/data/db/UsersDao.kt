package com.thelumiereguy.matchesapp.data.db

import androidx.room.*
import com.thelumiereguy.matchesapp.data.db.enitity.UsersEntity


@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user: List<UsersEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UsersEntity)

    @Query("SELECT * from UsersEntity")
    suspend fun getAllUsersLocal(): List<UsersEntity>

    @Query("SELECT * from UsersEntity WHERE favourite = 1")
    suspend fun getAllFavourites(): List<UsersEntity>

    @Query("DELETE FROM UsersEntity")
    fun nukeDB()
}