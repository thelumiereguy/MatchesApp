package com.thelumiereguy.matchesapp.di.builders

import androidx.lifecycle.ViewModel
import com.thelumiereguy.matchesapp.di.qualifierAnnotations.ViewModelKey
import com.thelumiereguy.matchesapp.ui.LauncherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilderWithKey {
    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: LauncherViewModel): ViewModel
}