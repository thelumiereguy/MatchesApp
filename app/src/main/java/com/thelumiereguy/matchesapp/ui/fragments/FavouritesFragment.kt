package com.thelumiereguy.matchesapp.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.adapters.FavouritesUserListAdapter
import com.thelumiereguy.matchesapp.ui.adapters.HomeUserListAdapter
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.ProfileClickListener
import com.thelumiereguy.matchesapp.ui.base.BaseFragment
import com.thelumiereguy.matchesapp.ui.viewmodels.FavouritesViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import com.thelumiereguy.matchesapp.utils.getClassTag
import javax.inject.Inject


class FavouritesFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contentProgressBar: ContentLoadingProgressBar
    private lateinit var noDataLayout: ConstraintLayout

    private lateinit var favouritesUserListAdapter: FavouritesUserListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favouritesViewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            viewModelFactory
        ).get(FavouritesViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        initUIComponents(view)
        startObserving()
        return view
    }

    private fun initUIComponents(view: View) {
        super.getActivityComponent()?.inject(this)
        recyclerView = view.findViewById(R.id.rv_favourites_list)
        contentProgressBar = view.findViewById(R.id.progressBar)
        noDataLayout = view.findViewById(R.id.l_no_data)

    }


    private fun startObserving() {

        /**
         * On Success, only hides the progress bar
         *
         * On Error, hides the list and shows error message
         */
        favouritesViewModel.getState().observe(viewLifecycleOwner, Observer { observable ->
            observable?.let {
                when (observable) {
                    is MainViewModel.HomeState.Ready -> {
                        hideProgressBar()
                    }
                    is MainViewModel.HomeState.Error -> {
                        hideList()
                        hideProgressBar()
                    }
                    is MainViewModel.HomeState.LoadingState -> {
                    }
                }
            }
        })


        /**
         * Checks if list is not empty
         *
         * Then handles it's visibility accordingly
         */
        favouritesViewModel.getUserListLiveData().observe(
            viewLifecycleOwner,
            Observer {
                if (it.results.isNotEmpty()) {
                    inflateRecyclerAdapter(it)
                    showList()
                } else {
                    hideList()
                }

            })


        favouritesViewModel.error.observe(viewLifecycleOwner, Observer { observable ->
            observable?.let {
                val errorMessage = StringBuilder()
                    .append(it.errorCode)
                    .append(" ")
                    .append(it.message)
                Log.w(getClassTag(), errorMessage.toString())
            }
        })

    }

    /**
     * if being called for the first time
     *
     * -> the adapter  will be initialised and set to the recylcerView
     *
     * else -> the list will updated
     */
    private fun inflateRecyclerAdapter(userList: UsersList) {
        if (!::favouritesUserListAdapter.isInitialized) {
            favouritesUserListAdapter = FavouritesUserListAdapter(userList, this)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = favouritesUserListAdapter
            }
        }
        favouritesUserListAdapter.favouritesUserList = userList
        favouritesUserListAdapter.notifyDataSetChanged()
    }

    private fun hideProgressBar() {
        contentProgressBar.visibility = View.GONE
    }


    /**
     * Gets the latest data from Room, when coming back to this screen
     */
    override fun onResume() {
        super.onResume()
        favouritesViewModel.getFavourites()
    }


    /**
     * Clears the reference of the ClickListener sent to the Adapter
     * to avoid memory leaks
     */
    override fun onDestroy() {
        super.onDestroy()
        if (::favouritesUserListAdapter.isInitialized) {
            favouritesUserListAdapter.onDestroy()
        }
    }

    private fun showList() {
        noDataLayout.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun hideList() {
        noDataLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

}
