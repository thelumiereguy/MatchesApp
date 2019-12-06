package com.thelumiereguy.matchesapp.data

import androidx.room.*
import com.thelumiereguy.matchesapp.UsersEntity
import com.thelumiereguy.matchesapp.UsersList


@Dao
interface UsersDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertFood(food: UsersEntity): Long


    @Query("SELECT * from UsersEntity")
    suspend fun getAllUsersLocal(): List<UsersEntity>

}