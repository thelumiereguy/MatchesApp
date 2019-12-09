package com.thelumiereguy.matchesapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thelumiereguy.matchesapp.data.repository_impl.UserRepositoryImplTest
import com.thelumiereguy.matchesapp.domain.enitity.UsersList

class MainViewModelTest {

    var userList: MutableLiveData<UsersList> =
        MutableLiveData(UserRepositoryImplTest().getUserList())

    lateinit var userListNull: MutableLiveData<UsersList>




    fun setStatus(index: Int, status: String) {
        userList.value?.let {
            with(it.results[index]) {
                this.status = status
            }
        }
    }


    fun setStatusToNull(index: Int, status: String) {
        with(userListNull.value!!.results[index]) {
            this.status = status
        }
    }



    fun setFavourite(index: Int) {
        userList.value?.let {
            val user = it.results[index]
            user.favourite = !(user.favourite)
        }
    }
}