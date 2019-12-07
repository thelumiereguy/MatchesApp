package com.thelumiereguy.matchesapp.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.FragmentHomeBinding
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.adapters.HomeUserListAdapter
import com.thelumiereguy.matchesapp.ui.base.BaseFragment
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import com.thelumiereguy.matchesapp.utils.Constants
import com.thelumiereguy.matchesapp.utils.getClassTag
import javax.inject.Inject


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val listenerImpl = ListenerImpl()
    private lateinit var homeUserListAdapter: HomeUserListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainViewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            viewModelFactory
        ).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )

        initUIComponents()
        startObserving()

        return binding.root
    }

    private fun startObserving() {

        mainViewModel.getState().observe(viewLifecycleOwner, Observer { observable ->
            observable?.let {
                when (observable) {
                    is MainViewModel.HomeState.Ready -> {
                        hideProgressBar()
                        updateScrollState()
                    }
                    is MainViewModel.HomeState.Error -> {
                        hideProgressBar()
                    }
                }
            }
        })

        mainViewModel.userList.observe(
            viewLifecycleOwner,
            Observer {
                Toast.makeText(requireContext(), it.results.size.toString(), Toast.LENGTH_LONG)
                    .show()
                inflateRecyclerAdapter(it)
            })


        mainViewModel.error.observe(viewLifecycleOwner, Observer { observable ->
            observable?.let {
                val errorMessage = StringBuilder()
                    .append(it.errorCode)
                    .append(" ")
                    .append(it.message)
                Log.w(getClassTag(), errorMessage.toString())
                Snackbar.make(
                    binding.root.findViewById(android.R.id.content),
                    errorMessage.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun updateScrollState() {
        mainViewModel.currentBrowsedPosition.let { position ->
            if (position != 0) {
                binding.rvUserList.setCurrentItem(position, false)
            }
        }
    }

    private fun inflateRecyclerAdapter(userList: UsersList) {
        if (!::homeUserListAdapter.isInitialized) {
            homeUserListAdapter = HomeUserListAdapter(userList, this)
            binding.rvUserList.apply {
                adapter = homeUserListAdapter
            }
        }
        homeUserListAdapter.usersList = userList
        homeUserListAdapter.notifyDataSetChanged()
    }

    private fun initUIComponents() {
        super.getActivityComponent()?.inject(this)
        binding.btnAccept.setOnClickListener(listenerImpl)
        binding.btnFavourite.setOnClickListener(listenerImpl)
        binding.btnDecline.setOnClickListener(listenerImpl)
        binding.rvUserList.registerOnPageChangeCallback(listenerImpl)

    }


    inner class ListenerImpl : View.OnClickListener, ViewPager2.OnPageChangeCallback() {

        override fun onClick(v: View?) {
            var position = binding.rvUserList.currentItem
            v?.let { view ->
                when (view.id) {
                    binding.btnAccept.id -> {
                        mainViewModel.setStatus(position, Constants.ACCEPTED)
                        position++
                        jumpToNextUser(position)
                    }

                    binding.btnDecline.id -> {
                        mainViewModel.setStatus(position, Constants.DECLINED)
                        position++
                        jumpToNextUser(position)
                    }


                    binding.btnFavourite.id -> {
                        mainViewModel.setFavourite(position)
                    }
                }
            }
        }

        private fun jumpToNextUser(position: Int) {
            if (position < mainViewModel.userList.value!!.results.size) {
                binding.rvUserList.setCurrentItem(position, true)
            }
        }


        override fun onPageSelected(position: Int) {
            mainViewModel.currentBrowsedPosition = position
            checkItemsLeft(position)
        }

    }

    private fun checkItemsLeft(position: Int) {
        mainViewModel.userList.value?.let {
            if (it.results.size - position == 5) {
                mainViewModel.getMoreItems()
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        if (::homeUserListAdapter.isInitialized) {
            homeUserListAdapter.onDestroy()
        }
    }


}
