package com.thelumiereguy.matchesapp.di.modules

import android.app.Application
import android.content.Context
import com.thelumiereguy.matchesapp.MatchesApplication
import com.thelumiereguy.matchesapp.di.qualifierAnnotations.ApplicationContext
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        SystemServiceModule::class,
        NetworkModule::class, DataModule::class]
)
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun bindApplication(application: MatchesApplication): Application

    @Binds
    @Singleton
    @ApplicationContext
    abstract fun bindApplicationContext(application: Application): Context
}