package com.thelumiereguy.matchesapp.di.builders

import androidx.lifecycle.ViewModel
import com.thelumiereguy.matchesapp.di.qualifierAnnotations.ViewModelKey
import com.thelumiereguy.matchesapp.ui.viewmodels.FavouritesViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.LauncherViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilderWithKey {
    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    abstract fun bindLauncherViewModel(launcherViewModel: LauncherViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun bindFavouriteViewModel(favouritesViewModel: FavouritesViewModel): ViewModel
}