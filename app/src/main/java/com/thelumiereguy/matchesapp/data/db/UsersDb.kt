package com.thelumiereguy.matchesapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thelumiereguy.matchesapp.data.db.enitity.UsersEntity


@Database(entities = [UsersEntity::class], exportSchema = true, version = UsersDb.VERSION)
abstract class UsersDb : RoomDatabase() {
    companion object {
        const val DB_NAME = "users.db"
        const val VERSION = 1
    }

    abstract fun getUserDao(): UsersDao
}
