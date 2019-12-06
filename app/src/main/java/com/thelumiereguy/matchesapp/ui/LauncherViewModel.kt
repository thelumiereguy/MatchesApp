package com.thelumiereguy.matchesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.usecases.GetAllUsersUseCase
import com.thelumiereguy.matchesapp.domain.usecases.InsertAllUsersUseCase
import javax.inject.Inject

class LauncherViewModel @Inject constructor(
    getAllUsersUseCase: GetAllUsersUseCase,
    private val insertAllUsersUseCase: InsertAllUsersUseCase
) : ViewModel() {

    val error: MutableLiveData<ErrorModel> by lazy { MutableLiveData<ErrorModel>() }


    init {
        getAllUsersUseCase.execute {
            onComplete {
                insertAllUsers(it)
            }

            onError { throwable ->
                error.value = throwable

            }
        }
    }


    private fun insertAllUsers(userList:UsersList) {
        insertAllUsersUseCase.userList = userList
        insertAllUsersUseCase.execute {
            onError { throwable ->
                error.value = throwable
            }
        }
    }


    // Encapsulate access to mutable LiveData through getter
    private val launcherState: MutableLiveData<LauncherState> =
        MutableLiveData(LauncherState.LoadingState())

    fun getState(): LiveData<LauncherState> = launcherState


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