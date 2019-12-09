package com.thelumiereguy.matchesapp.data.network_service

import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface NetworkService {

    /**
     * Gets the list of 10 users from API
     *
     * @return a response wrapped by
     *
     * @see Deferred
     *
     * it must be awaited till completion (Same as async/ await in JS)
     *
     */
    @GET("api/?results=10")
    fun getAllUsersAsync(
    ): Deferred<UsersList>


}