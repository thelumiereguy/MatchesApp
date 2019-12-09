package com.thelumiereguy.matchesapp.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelumiereguy.matchesapp.di.scopeAnnotations.ActivityScope
import javax.inject.Inject
import javax.inject.Provider

@ActivityScope
class ViewModelFactory
@Inject constructor(private val viewmodelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelProvider.Factory {

    /**
     * gets the viewmodel object from the Map ,based on its key
     *
     * If the key is not found, it'll throw an exception
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val creator = viewmodelMap[modelClass] ?: viewmodelMap.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}