package com.thelumiereguy.matchesapp.domain.enitity

sealed class Status {
    object Accepted : Status()
    object Declined : Status()
}