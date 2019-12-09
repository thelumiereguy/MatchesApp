package com.thelumiereguy.matchesapp.data.db.enitity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thelumiereguy.matchesapp.domain.enitity.UsersList

@Entity
data class UsersEntity @JvmOverloads constructor(

    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String = "",

    @ColumnInfo(name = "cell")
    val cell: String = "",


    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "date")
    val date: String,


    @ColumnInfo(name = "gender")
    val gender: String,


    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "city")
    val city: String,


    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "postcode")
    val postcode: String,
    @ColumnInfo(name = "state")
    val state: String,

    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,

    @ColumnInfo(name = "streetName")
    val streetName: String,
    @ColumnInfo(name = "number")
    val number: Int,


    @ColumnInfo(name = "first")
    val first: String,
    @ColumnInfo(name = "last")
    val last: String,
    @ColumnInfo(name = "title")
    val title: String,


    @ColumnInfo(name = "phone")
    val phone: String,


    @ColumnInfo(name = "large")
    val large: String,
    @ColumnInfo(name = "medium")
    val medium: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "registeredAge")
    val registeredAge: Int,
    @ColumnInfo(name = "registeredDate")
    val registeredDate: String,

    val status: String?,

    val favourite: Boolean
) {


    fun mapToUser(): UsersList.User {
        return UsersList.User(
            cell = cell,
            dob = UsersList.User.Dob(age, date),
            email = email,
            gender = gender,
            id = UsersList.User.Id(name, ""),
            location = UsersList.User.Location(
                city = city,
                coordinates = UsersList.User.Location.Coordinates(
                    latitude, longitude
                ),
                country = country,
                postcode = postcode,
                state = state,
                street = UsersList.User.Location.Street(
                    name = streetName,
                    number = number
                ),
                timezone = UsersList.User.Location.Timezone(
                    "",
                    ""
                )
            ),
            name = UsersList.User.Name(
                first,
                last,
                title
            ),
            nat = "",
            phone = phone,
            picture = UsersList.User.Picture(
                large, medium, thumbnail
            ),
            registered = UsersList.User.Registered(
                registeredAge,
                registeredDate
            ),
            login = UsersList.User.Login(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            ),
            favourite = favourite,
            status = status
        )
    }

    companion object {
        fun mapFrom(user: UsersList.User): UsersEntity {
            return UsersEntity(
                cell = user.cell,
                age = user.dob.age,
                date = user.dob.date,
                email = user.email,
                gender = user.gender,
                name = user.id.name,
                city = user.location.city,
                longitude = user.location.coordinates.longitude,
                latitude = user.location.coordinates.latitude,
                country = user.location.country,
                state = user.location.state,
                postcode = user.location.postcode,
                streetName = user.location.street.name,
                number = user.location.street.number,
                first = user.name.first,
                last = user.name.last,
                title = user.name.title,
                phone = user.phone,
                medium = user.picture.medium,
                large = user.picture.large,
                thumbnail = user.picture.thumbnail,
                registeredAge = user.registered.age,
                registeredDate = user.registered.date,
                favourite = user.favourite,
                status = user.status
            )
        }
    }

}