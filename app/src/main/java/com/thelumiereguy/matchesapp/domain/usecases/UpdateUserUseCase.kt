package com.thelumiereguy.matchesapp.domain.usecases


import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) :  BaseUseCase<Unit>() {

    lateinit var userToUpdate :UsersList.User

    override suspend fun executeOnBackground() {
        userRepository.updateUser(userToUpdate)
    }

}