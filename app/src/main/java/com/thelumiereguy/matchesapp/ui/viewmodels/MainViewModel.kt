package com.thelumiereguy.matchesapp.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.usecases.GetAllUsersUseCase
import com.thelumiereguy.matchesapp.domain.usecases.InsertAllUsersUseCase
import com.thelumiereguy.matchesapp.domain.usecases.UpdateUserUseCase
import com.thelumiereguy.matchesapp.utils.Utils
import com.thelumiereguy.matchesapp.utils.getClassTag
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val insertAllUsersUseCase: InsertAllUsersUseCase
) : AndroidViewModel(application) {

    private val homeState: MutableLiveData<HomeState> =
        MutableLiveData(HomeState.LoadingState)


    val error: MutableLiveData<ErrorModel> by lazy { MutableLiveData<ErrorModel>() }
    val userList: MutableLiveData<UsersList> by lazy { MutableLiveData<UsersList>() }
    var currentBrowsedPosition: Int = 0

    init {

        getAllUsersUseCase.isInternetConnected = false

        getAllUsersUseCase.execute {
            onComplete {
                userList.postValue(it)
                homeState.postValue(HomeState.Ready)
            }

            onError { throwable ->
                error.value = throwable
                homeState.postValue(HomeState.Error)
            }
        }
    }


    fun setStatus(index: Int, status: String) {
        userList.value?.let {
            with(it.results[index]) {
                this.status = status
                updateUserUseCase.userToUpdate = this
            }
        }

    }


    fun getState(): LiveData<HomeState> = homeState


    fun setFavourite(index: Int) {
        userList.value?.let {
            val user = it.results[index]
            user.favourite = !(user.favourite)
            updateUserUseCase.userToUpdate = user
            updateUser()
        }
    }

    private fun updateUser() {
        updateUserUseCase.execute {
            onComplete {
                Log.d(getClassTag(), " Inserted ")
            }

            onError { throwable ->
                error.value = throwable
//                    homeState.postValue(HomeState.Error)
            }
        }
    }

    fun getMoreItems() {
        getAllUsersUseCase.isInternetConnected = true

        getAllUsersUseCase.execute {
            onComplete { newList ->
                appendToList(newList)
            }

            onError { throwable ->
                error.value = throwable
                homeState.postValue(HomeState.Error)
            }
        }
    }

    private fun appendToList(newList: UsersList) {
        val userTemp = userList.value
        val tempList = userTemp?.results?.toMutableList()
        tempList?.addAll(newList.results)
        tempList?.let {
            userTemp.results = tempList
        }
        userList.postValue(userTemp)
        userTemp?.let {
            insertAllUsers(it)
        }
        homeState.postValue(HomeState.Ready)
    }

    private fun insertAllUsers(userList: UsersList) {
        insertAllUsersUseCase.userList = userList
        insertAllUsersUseCase.execute {
            onError { throwable ->
                error.value = throwable
            }
        }
    }

    sealed class HomeState {
        object LoadingState : HomeState()
        object Ready : HomeState()
        object Error : HomeState()
    }


}