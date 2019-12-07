package com.thelumiereguy.matchesapp.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.usecases.GetAllUsersUseCase
import com.thelumiereguy.matchesapp.domain.usecases.GetFavouritesUseCase
import com.thelumiereguy.matchesapp.domain.usecases.InsertAllUsersUseCase
import com.thelumiereguy.matchesapp.domain.usecases.UpdateUserUseCase
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModel.HomeState
import com.thelumiereguy.matchesapp.utils.Utils
import com.thelumiereguy.matchesapp.utils.getClassTag
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {


    private val homeState: MutableLiveData<HomeState> =
        MutableLiveData(HomeState.LoadingState)

    fun getState(): LiveData<HomeState> = homeState


    val error: MutableLiveData<ErrorModel> by lazy { MutableLiveData<ErrorModel>() }
    private val userList: MutableLiveData<UsersList> by lazy { MutableLiveData<UsersList>() }

    init {
        getFavourites()
    }


    fun getUserListLiveData(): LiveData<UsersList> {
        getFavourites()
        return userList
    }


    private fun getFavourites() {
        getFavouritesUseCase.execute {
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
            it.results[index].status = status
        }
    }


}