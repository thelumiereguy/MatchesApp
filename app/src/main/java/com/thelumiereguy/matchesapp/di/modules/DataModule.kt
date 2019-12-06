package com.thelumiereguy.matchesapp.di.modules

import android.app.Application
import androidx.room.Room
import com.thelumiereguy.matchesapp.data.db.UsersDao
import com.thelumiereguy.matchesapp.data.db.UsersDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): UsersDb {
        return Room
            .databaseBuilder(application, UsersDb::class.java, UsersDb.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(appDataBase: UsersDb): UsersDao {
        return appDataBase.getUserDao()
    }
}