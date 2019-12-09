package com.thelumiereguy.matchesapp.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.thelumiereguy.matchesapp.di.qualifierAnnotations.ApplicationContext
import com.thelumiereguy.matchesapp.di.scopeAnnotations.ActivityScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SystemServiceModule {

    companion object {
        const val shared_pref_name = "com.thelumiereguy.matchesapp"
    }

    @Provides
    @ActivityScope
    fun provideSharedPreferences(context: Application): SharedPreferences =
        context.applicationContext.getSharedPreferences(
            shared_pref_name,
            Context.MODE_PRIVATE
        )

}