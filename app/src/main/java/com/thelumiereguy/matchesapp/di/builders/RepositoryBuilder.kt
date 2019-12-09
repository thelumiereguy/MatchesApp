package com.thelumiereguy.matchesapp.di.builders

import com.thelumiereguy.matchesapp.data.repository_impl.UserRepositoryImpl
import com.thelumiereguy.matchesapp.domain.repository.PreferencesHelper
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryBuilder {


    /**
     * Binds the implementation of
     * @see UserRepository through UserRepositoryImpl
     *
     */
    @Binds
    abstract fun bindsMovieRepository(userRepositoryI: UserRepositoryImpl): UserRepository
}