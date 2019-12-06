package com.thelumiereguy.matchesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LauncherViewModel : ViewModel() {


    // GOOD: Encapsulate access to mutable LiveData through getter
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