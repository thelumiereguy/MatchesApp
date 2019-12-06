package com.thelumiereguy.matchesapp


import com.google.gson.annotations.SerializedName

data class UsersList constructor(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<User>
) {
    data class Info(
        @SerializedName("page")
        val page: Int = -1,
        @SerializedName("results")
        val results: Int = -1,
        @SerializedName("seed")
        val seed: String = "",
        @SerializedName("version")
        val version: String = ""
    )

    data class User(
        @SerializedName("cell")
        val cell: String,
        @SerializedName("dob")
        val dob: Dob,
        @SerializedName("email")
        val email: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("id")
        val id: Id,
        @SerializedName("location")
        val location: Location,
        @SerializedName("login")
        val login: Login,
        @SerializedName("name")
        val name: Name,
        @SerializedName("nat")
        val nat: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("picture")
        val picture: Picture,
        @SerializedName("registered")
        val registered: Registered
    ) {

        data class Dob(
            @SerializedName("age")
            val age: Int,
            @SerializedName("date")
            val date: String
        )

        data class Id(
            @SerializedName("name")
            val name: String,
            @SerializedName("value")
            val value: Any?
        )

        data class Location(
            @SerializedName("city")
            val city: String,
            @SerializedName("coordinates")
            val coordinates: Coordinates,
            @SerializedName("country")
            val country: String,
            @SerializedName("postcode")
            val postcode: Int,
            @SerializedName("state")
            val state: String,
            @SerializedName("street")
            val street: Street,
            @SerializedName("timezone")
            val timezone: Timezone
        ) {
            data class Coordinates(
                @SerializedName("latitude")
                val latitude: String,
                @SerializedName("longitude")
                val longitude: String
            )

            data class Street(
                @SerializedName("name")
                val name: String,
                @SerializedName("number")
                val number: Int
            )

            data class Timezone(
                @SerializedName("description")
                val description: String,
                @SerializedName("offset")
                val offset: String
            )
        }

        data class Login(
            @SerializedName("md5")
            val md5: String,
            @SerializedName("password")
            val password: String,
            @SerializedName("salt")
            val salt: String,
            @SerializedName("sha1")
            val sha1: String,
            @SerializedName("sha256")
            val sha256: String,
            @SerializedName("username")
            val username: String,
            @SerializedName("uuid")
            val uuid: String
        )

        data class Name(
            @SerializedName("first")
            val first: String,
            @SerializedName("last")
            val last: String,
            @SerializedName("title")
            val title: String
        )

        data class Picture(
            @SerializedName("large")
            val large: String,
            @SerializedName("medium")
            val medium: String,
            @SerializedName("thumbnail")
            val thumbnail: String
        )

        data class Registered(
            @SerializedName("age")
            val age: Int,
            @SerializedName("date")
            val date: String
        )
    }
}