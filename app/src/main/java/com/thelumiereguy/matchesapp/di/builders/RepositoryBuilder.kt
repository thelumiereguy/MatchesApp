package com.thelumiereguy.matchesapp.di.builders

import com.thelumiereguy.matchesapp.data.repository_impl.UserRepositoryImpl
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryBuilder {
    @Binds
    abstract fun bindsMovieRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}