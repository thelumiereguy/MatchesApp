package com.thelumiereguy.matchesapp.data.db

import androidx.room.*
import com.thelumiereguy.matchesapp.data.db.enitity.UsersEntity


@Dao
interface UsersDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertFood(food: UsersEntity): Long


    @Query("SELECT * from UsersEntity")
    suspend fun getAllUsersLocal(): List<UsersEntity>

}