package com.thelumiereguy.matchesapp.di.builders

import androidx.lifecycle.ViewModelProvider
import com.thelumiereguy.matchesapp.ui.factory.ViewModelFactory
import dagger.Binds
import dagger.Module


@Module(
    includes = [
        (RepositoryBuilder::class),
        (ViewModelBuilderWithKey::class)
    ]
)
abstract class ViewModelFactoryBuilder {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}