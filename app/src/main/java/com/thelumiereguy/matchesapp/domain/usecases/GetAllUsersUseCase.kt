package com.thelumiereguy.matchesapp.domain.usecases

import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<UsersList>() {

    private var isInternetConnected:Boolean = true

    override suspend fun executeOnBackground(): UsersList {
        return userRepository.getUsersList(isInternetConnected)
    }

}