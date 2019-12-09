package com.thelumiereguy.matchesapp.ui.fragments


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.thelumiereguy.matchesapp.utils.Utils
import com.thelumiereguy.matchesapp.utils.Utils.showRelevantSnack
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


        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )

        initUIComponents()
        startObserving()
        return binding.root
    }



    private fun initUIComponents() {
        super.getActivityComponent()?.inject(this)
        binding.btnAccept.setOnClickListener(listenerImpl)
        binding.btnFavourite.setOnClickListener(listenerImpl)
        binding.btnDecline.setOnClickListener(listenerImpl)
        binding.vpUserList.registerOnPageChangeCallback(listenerImpl)

    }


    private fun startObserving() {

        mainViewModel.getState().observe(viewLifecycleOwner, Observer { observable ->
            observable?.let {
                when (observable) {
                    is MainViewModel.HomeState.Ready -> {
                        hideProgressBar()
                    }
                    is MainViewModel.HomeState.Error -> {
                        hideProgressBar()
                        handleError()
                    }
                }
            }
        })

        mainViewModel.getUserListLiveData().observe(
            viewLifecycleOwner,
            Observer {
                if (it.results.isNotEmpty()) {
                    inflateRecyclerAdapter(it)
                    updateScrollState()
                }
            })


        mainViewModel.error.observe(viewLifecycleOwner, Observer { observable ->
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
     * Called to update the last scrolled position
     * when coming back to this screen
     *
     * The position is temporarily stored in the ViewModel
     */
    private fun updateScrollState() {
        mainViewModel.currentBrowsedPosition.let { position ->
            homeUserListAdapter.notifyItemChanged(position)
            binding.vpUserList.setCurrentItem(position, false)
            if (position == 0) {
                val user = mainViewModel.getUserListLiveData().value
                user?.let { setButtonVisibility(it.results[position]) }
            }
        }
    }

    private fun inflateRecyclerAdapter(userList: UsersList) {
        if (!::homeUserListAdapter.isInitialized) {
            showList()
            homeUserListAdapter = HomeUserListAdapter(userList, this)
            binding.vpUserList.apply {
                adapter = homeUserListAdapter
            }
        }
        homeUserListAdapter.setUser(userList)
    }


    inner class ListenerImpl : View.OnClickListener, ViewPager2.OnPageChangeCallback() {


        /**
         *
         * When a button is clicked, the list in the viewmodel is updated and
         * the DB as well.
         *
         * If Accept/Decline Button is pressed, their visibility
         * will be toggled as well
         *
         * And the current page would be incremented as well
         *
         */
        override fun onClick(v: View) {
            var position = binding.vpUserList.currentItem
            val user = mainViewModel.userList.value?.let {
                it.results[position]
            }
            user?.let {
                when (v.id) {
                    binding.btnAccept.id -> {
                        homeUserListAdapter.setStatus(position, Constants.ACCEPTED)
                        mainViewModel.setStatus(position, Constants.ACCEPTED)
                        showRelevantSnack(
                            Utils.TYPE.ACCEPTED(user.getFullName()),
                            binding.rootCoordinator
                        )
                        setButtonVisibility(user)
                        position++
                        jumpToNextUser(position)

                    }

                    binding.btnDecline.id -> {
                        mainViewModel.setStatus(position, Constants.DECLINED)
                        homeUserListAdapter.setStatus(position, Constants.DECLINED)
                        showRelevantSnack(
                            Utils.TYPE.DECLINED(user.getFullName()),
                            binding.rootCoordinator
                        )
                        position++
                        setButtonVisibility(user)
                        jumpToNextUser(position)
                    }


                    binding.btnFavourite.id -> {
                        mainViewModel.setFavourite(position)
                        showRelevantSnack(
                            Utils.TYPE.FAVOURITED(!user.favourite, user.getFullName()),
                            binding.rootCoordinator
                        )
                    }
                }
            }
        }


        private fun jumpToNextUser(position: Int) {
            if (position < mainViewModel.userList.value!!.results.size) {
                Handler().postDelayed({
                    binding.vpUserList.setCurrentItem(position, true)
                }, 500)

            }
        }

        /**
         * When a page is selected, it updates the visibility of the buttons
         * based on their profile status
         *
         * And updates the current position in the ViewModel
         */
        override fun onPageSelected(position: Int) {
            val user = mainViewModel.userList.value?.let { it.results[position] }
            setButtonVisibility(user)
            mainViewModel.currentBrowsedPosition = position
            checkItemsLeft(position)
        }
    }

    private fun setButtonVisibility(user: UsersList.User?) {
        binding.buttonGroup.visibility = if (user?.status == null) View.VISIBLE else View.INVISIBLE
        binding.buttonGroup.requestLayout()
    }

    /**
     * Checks the number of items left in the userList
     *
     * If its less the 5, the viewmodel fetches more data
     *
     * @param position Current page position
     */
    private fun checkItemsLeft(position: Int) {
        mainViewModel.userList.value?.let {
            if (it.results.size - position < 5) {
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


    private fun showList() {
        binding.vpUserList.visibility = View.VISIBLE
        binding.btnGroup.visibility = View.VISIBLE
        binding.llNoInternet.clNoDataMain.visibility = View.GONE
    }


    private fun handleError() {
        val user = mainViewModel.userList.value
        user.let {
            if (it == null || it.results.isEmpty()) {
                binding.vpUserList.visibility = View.GONE
                binding.btnGroup.visibility = View.GONE
                binding.llNoInternet.clNoDataMain.visibility = View.VISIBLE
            }
        }
    }


    override fun onResume() {
        super.onResume()
        mainViewModel.getFromDb()
    }

}
