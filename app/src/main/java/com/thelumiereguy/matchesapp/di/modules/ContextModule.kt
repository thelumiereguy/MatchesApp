package com.thelumiereguy.matchesapp.di.modules

import android.app.Application
import android.content.Context
import com.thelumiereguy.matchesapp.di.builders.ViewModelFactoryBuilder
import com.thelumiereguy.matchesapp.di.qualifierAnnotations.ApplicationContext
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelFactoryBuilder::class])
class ContextModule (private val application: Application) {


    @Provides
    fun provideContext(): Application {
        return application
    }
}