package com.thelumiereguy.matchesapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.domain.usecases.GetFavouritesUseCase
import com.thelumiereguy.matchesapp.domain.usecases.UpdateUserUseCase
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModel.HomeState
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


     fun getFavourites() {
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

    override fun onCleared() {
        super.onCleared()
        getFavouritesUseCase.unsubscribe()
        updateUserUseCase.unsubscribe()
    }


}