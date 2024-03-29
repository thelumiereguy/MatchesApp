package com.thelumiereguy.matchesapp

import android.app.Application
import com.thelumiereguy.matchesapp.di.components.DaggerApplicationComponent

class MatchesApplication:Application() {

    val applicationInjector = DaggerApplicationComponent.builder()
        .application(this)
        .build()

}