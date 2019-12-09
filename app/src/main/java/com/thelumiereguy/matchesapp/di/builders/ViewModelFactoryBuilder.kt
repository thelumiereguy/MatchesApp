package com.thelumiereguy.matchesapp.di.builders

import androidx.lifecycle.ViewModelProvider
import com.thelumiereguy.matchesapp.domain.repository.PreferencesHelper
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import dagger.Binds
import dagger.Module


@Module(
    includes = [
        (RepositoryBuilder::class),
        (ViewModelBuilderWithKey::class),
        PreferencesHelperBuilder::class
    ]
)
abstract class ViewModelFactoryBuilder {


    /**
     * Binds the implementation of
     * @see ViewModelProvider.Factory through ViewModelFactory
     *
     */
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}