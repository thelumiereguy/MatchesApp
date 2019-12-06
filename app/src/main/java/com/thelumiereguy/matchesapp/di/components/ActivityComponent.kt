package com.thelumiereguy.matchesapp.di.components

import com.thelumiereguy.matchesapp.di.modules.ApplicationModule
import com.thelumiereguy.matchesapp.di.modules.DataModule
import com.thelumiereguy.matchesapp.di.modules.NetworkModule
import com.thelumiereguy.matchesapp.di.modules.SystemServiceModule
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import dagger.Component

@Component(modules = [DataModule::class, NetworkModule::class, SystemServiceModule::class])
interface ActivityComponent {
    fun inject(activity: BaseActivity?)
}