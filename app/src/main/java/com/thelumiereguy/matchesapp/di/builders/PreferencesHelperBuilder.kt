package com.thelumiereguy.matchesapp.di.builders

import com.thelumiereguy.matchesapp.data.shared_preferences.PreferencesHelperImpl
import com.thelumiereguy.matchesapp.domain.repository.PreferencesHelper
import dagger.Binds
import dagger.Module

@Module
abstract class PreferencesHelperBuilder {
    @Binds
    abstract fun bindsPreferencesHelper(preferencesHelperImpl: PreferencesHelperImpl): PreferencesHelper
}