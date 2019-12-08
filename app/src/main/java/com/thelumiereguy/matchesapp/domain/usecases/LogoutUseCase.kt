package com.thelumiereguy.matchesapp.domain.usecases


import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) :  BaseUseCase<Unit>() {

    override suspend fun executeOnBackground() {
        userRepository.logoutUser()
    }

}