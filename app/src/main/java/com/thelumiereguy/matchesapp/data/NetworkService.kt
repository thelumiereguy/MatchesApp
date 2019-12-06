package com.thelumiereguy.matchesapp.data

import com.thelumiereguy.matchesapp.UsersList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface NetworkService {

        @GET("api/")
        fun getAllUsers(
        ): Deferred<UsersList>


}