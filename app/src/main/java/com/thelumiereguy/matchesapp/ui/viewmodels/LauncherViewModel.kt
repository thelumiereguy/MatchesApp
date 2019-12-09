package com.thelumiereguy.matchesapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thelumiereguy.matchesapp.data.shared_preferences.PreferencesHelperImpl
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.usecases.GetAllUsersUseCase
import com.thelumiereguy.matchesapp.domain.usecases.InsertAllUsersUseCase
import javax.inject.Inject

class LauncherViewModel @Inject constructor(
    preferencesHelperImpl: PreferencesHelperImpl,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val insertAllUsersUseCase: InsertAllUsersUseCase
) : ViewModel() {

    val error: MutableLiveData<ErrorModel> by lazy { MutableLiveData<ErrorModel>() }


    /**
     * If user is not logged in, Remote Data will be fetched
     * and cached into DB
     */
    init {
        if (!preferencesHelperImpl.getLoggedIn()) {
            getAllUsersUseCase.execute {
                onComplete {
                    insertAllUsers(it)
                }
                onError { throwable ->
                    error.value = throwable
                }
            }
        }

    }



    private fun insertAllUsers(userList: UsersList) {
        insertAllUsersUseCase.userList = userList
        insertAllUsersUseCase.execute {
            onError { throwable ->
                error.value = throwable
            }
        }
    }


    private val launcherState: MutableLiveData<LauncherState> =
        MutableLiveData(LauncherState.LoadingState)

    fun getState(): LiveData<LauncherState> = launcherState


    fun showHome() {
        launcherState.postValue(LauncherState.Home)
    }

    fun showLogin() {
        launcherState.postValue(LauncherState.Login)
    }


    fun showOnBoarding() {
        launcherState.postValue(LauncherState.OnBoarding)
    }

    sealed class LauncherState {
        object LoadingState : LauncherState()
        object OnBoarding : LauncherState()
        object Login : LauncherState()
        object Home : LauncherState()
    }

    override fun onCleared() {
        super.onCleared()
        insertAllUsersUseCase.unsubscribe()
        getAllUsersUseCase.unsubscribe()
    }
}