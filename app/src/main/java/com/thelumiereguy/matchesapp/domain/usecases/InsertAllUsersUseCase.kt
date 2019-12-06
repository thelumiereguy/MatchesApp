package com.thelumiereguy.matchesapp.domain.usecases


import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import javax.inject.Inject

class InsertAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) :  BaseUseCase<Unit>() {

    lateinit var userList:UsersList

    override suspend fun executeOnBackground() {
        userRepository.saveAllUsers(userList)
    }

}