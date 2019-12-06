package com.thelumiereguy.matchesapp.usecases

import com.thelumiereguy.matchesapp.UsersList
import com.thelumiereguy.matchesapp.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val isInternetConnected:Boolean,
    private val userRepository: UserRepository
) : BaseUseCase<UsersList>() {

    override suspend fun executeOnBackground(): UsersList {
        return userRepository.getUsersList(isInternetConnected)
    }

}