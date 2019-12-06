package com.thelumiereguy.matchesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thelumiereguy.matchesapp.domain.usecases.GetAllUsersUseCase
import javax.inject.Inject

class LauncherViewModel  @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    // Encapsulate access to mutable LiveData through getter
    private val launcherState: MutableLiveData<LauncherState> =
        MutableLiveData(LauncherState.LoadingState())

    fun getState(): LiveData<LauncherState> = launcherState


    fun showLoading() {
        launcherState.postValue(LauncherState.LoadingState())
    }

    fun showHome() {
        launcherState.postValue(LauncherState.Home())
    }


    fun showOnBoarding() {
        launcherState.postValue(LauncherState.OnBoarding())
    }

    sealed class LauncherState {
        class LoadingState : LauncherState()
        class OnBoarding : LauncherState()
        class Home : LauncherState()
    }
}