package com.thelumiereguy.matchesapp.di.components

import com.thelumiereguy.matchesapp.di.modules.ContextModule
import com.thelumiereguy.matchesapp.di.modules.DataModule
import com.thelumiereguy.matchesapp.di.modules.NetworkModule
import com.thelumiereguy.matchesapp.di.modules.SystemServiceModule
import com.thelumiereguy.matchesapp.di.scopeAnnotations.ActivityScope
import com.thelumiereguy.matchesapp.ui.activities.HomeActivity
import com.thelumiereguy.matchesapp.ui.activities.LauncherActivity
import com.thelumiereguy.matchesapp.ui.activities.UserDetailActivity
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.ui.fragments.*
import dagger.Component


@ActivityScope
@Component(
    modules = [
        NetworkModule::class,
        ContextModule::class,
        DataModule::class,
        SystemServiceModule::class]
)

interface ActivityComponent {

    fun inject(activity: LauncherActivity)
    fun inject(activity: HomeActivity)
    fun inject(splashFragment: SplashFragment)
    fun inject(onBoardingFragment: OnBoardingFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(favouritesFragment: FavouritesFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(userDetailActivity: UserDetailActivity) {

    }
}