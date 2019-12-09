package com.thelumiereguy.matchesapp.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.adapters.HomeUserListAdapter
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModelTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class HomeFragmentTest {


    private lateinit var viewModel: MainViewModelTest

    val observer: Observer<UsersList> = Observer {}

    lateinit var homeUserListAdapter: HomeUserListAdapter

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        viewModel = MainViewModelTest()
        viewModel.userList.observeForever(observer)
//        viewModel.userListNull.observeForever(observer)
    }


    /**
     * Action buttons should be hidden if input data is null
     */
    @Test
    fun test_setStatus_nullData_returns_fail() {
        try {
            viewModel.setStatusToNull(0, "Accepted")
            assertEquals("Accepted", viewModel.userListNull.value!!.results[0].status)
        } catch (e: Exception) {

        } finally {
            viewModel.userList.removeObserver(observer)
        }
    }


    @Test
    fun test_setFavourite_index5_returns_true() {
        try {
            viewModel.setFavourite(0)
            assertEquals(true, viewModel.userList.value!!.results[0].favourite)
        } catch (e: Exception) {
        } finally {
            viewModel.userList.removeObserver(observer)
        }
    }

    /**
     * Check size before jumping to next user
     */
    @Test
    fun test_jumpToNextUser_index5_returns_fail() {
        assertEquals(viewModel.userList.value!!.results[9], jumpToNextUser(10))
        viewModel.userList.removeObserver(observer)
    }


    /**
     * Check list size/null before sending it to the adapter or setting it up with viewpager
     */
    @Test
    fun test_inflateAdapter_nullList_returns_fail() {
        inflateRecyclerAdapter(viewModel.userList.value!!)
        assertEquals("Accepted",homeUserListAdapter.usersList.results[4].status)
        viewModel.userList.removeObserver(observer)
    }


    private fun jumpToNextUser(position: Int): UsersList.User {
        return viewModel.userList.value!!.results[position]
        TODO("Checking list size before jumping")
    }


    private fun inflateRecyclerAdapter(userList: UsersList) {
        homeUserListAdapter = HomeUserListAdapter(userList, null)
        homeUserListAdapter.setStatus(4, "Accepted")
    }


}