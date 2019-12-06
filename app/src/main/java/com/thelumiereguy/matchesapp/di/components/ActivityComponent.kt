package com.thelumiereguy.matchesapp.di.components

import com.thelumiereguy.matchesapp.di.modules.ContextModule
import com.thelumiereguy.matchesapp.di.modules.DataModule
import com.thelumiereguy.matchesapp.di.modules.NetworkModule
import com.thelumiereguy.matchesapp.di.scopeAnnotations.ActivityScope
import com.thelumiereguy.matchesapp.ui.activities.LauncherActivity
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.ui.fragments.OnBoardingFragment
import com.thelumiereguy.matchesapp.ui.fragments.SplashFragment
import dagger.Component


@ActivityScope
@Component(
    modules = [
        NetworkModule::class,
        ContextModule::class,
        DataModule::class]
)
interface ActivityComponent {
    fun inject(activity: BaseActivity)
    fun inject(activity: LauncherActivity)
    fun inject(splashFragment: SplashFragment)
    fun inject(onBoardingFragment: OnBoardingFragment)
}