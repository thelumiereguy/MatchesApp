package com.thelumiereguy.matchesapp.di.components


import com.thelumiereguy.matchesapp.MatchesApplication
import com.thelumiereguy.matchesapp.di.modules.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

        @Component.Builder
        interface Builder {

                @BindsInstance
                fun application(application: MatchesApplication): Builder

                fun build(): ApplicationComponent
        }

}