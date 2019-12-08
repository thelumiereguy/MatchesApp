package com.thelumiereguy.matchesapp.di.modules

import android.app.Application
import androidx.room.Room
import com.thelumiereguy.matchesapp.data.db.UsersDao
import com.thelumiereguy.matchesapp.data.db.UsersDb
import com.thelumiereguy.matchesapp.di.qualifierAnnotations.ApplicationContext
import com.thelumiereguy.matchesapp.di.scopeAnnotations.ActivityScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideRoomDatabase(application: Application): UsersDb {
        return Room
            .databaseBuilder(application.applicationContext, UsersDb::class.java, UsersDb.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(appDataBase: UsersDb): UsersDao {
        return appDataBase.getUserDao()
    }
}