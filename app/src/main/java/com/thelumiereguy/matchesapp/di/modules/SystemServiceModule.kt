package com.thelumiereguy.matchesapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.thelumiereguy.matchesapp.di.qualifierAnnotations.ApplicationContext
import com.thelumiereguy.matchesapp.ui.factory.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object SystemServiceModule {


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
         context.applicationContext.getSharedPreferences("com.thelumiereguy.matchesapp",Context.MODE_PRIVATE)

}