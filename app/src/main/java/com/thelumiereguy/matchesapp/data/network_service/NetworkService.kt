package com.thelumiereguy.matchesapp.data.network_service

import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface NetworkService {

        @GET("api/")
        fun getAllUsers(
        ): Deferred<UsersList>


}