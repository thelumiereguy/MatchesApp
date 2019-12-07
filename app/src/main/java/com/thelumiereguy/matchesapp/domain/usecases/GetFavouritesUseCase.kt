package com.thelumiereguy.matchesapp.domain.usecases


import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.repository.UserRepository
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<UsersList>() {


    override suspend fun executeOnBackground(): UsersList {
        return userRepository.getFavourites()
    }

}