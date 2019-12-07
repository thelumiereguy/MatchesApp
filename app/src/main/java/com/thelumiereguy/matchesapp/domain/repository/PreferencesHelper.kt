package com.thelumiereguy.matchesapp.domain.repository

interface PreferencesHelper {
    fun setLoggedIn()

    fun logout()

    fun getLoggedIn():Boolean
}