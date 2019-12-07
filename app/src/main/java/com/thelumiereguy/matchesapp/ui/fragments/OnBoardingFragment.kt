package com.thelumiereguy.matchesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.thelumiereguy.matchesapp.ui.ui_models.OnBoardingPageContent
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.FragmentOnBoardingBinding
import com.thelumiereguy.matchesapp.ui.viewmodels.LauncherViewModel
import com.thelumiereguy.matchesapp.ui.adapters.OnBoardingViewPagerAdapter
import com.thelumiereguy.matchesapp.ui.base.BaseFragment
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import javax.inject.Inject


class OnBoardingFragment : BaseFragment() {

    private lateinit var binding: FragmentOnBoardingBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var launcherViewModel: LauncherViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_on_boarding, container, false)
        connectUIComponents()
        return binding.root
    }

    private fun connectUIComponents() {
        super.getActivityComponent()?.inject(this)
        launcherViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(
            LauncherViewModel::class.java
        )


        binding.vpOnboarding.apply {
            adapter =
                OnBoardingViewPagerAdapter(
                    OnBoardingPageContent.getOnBoardingContent()
                )
        }
        binding.btnNext.setOnClickListener(ClickListenerImpl())
    }




    private inner class ClickListenerImpl : View.OnClickListener {
        override fun onClick(v: View?) {
            when(binding.vpOnboarding.currentItem){
                1 -> {
                    launcherViewModel.showLogin()
                }
                else -> {
                    binding.btnNext.text = "Start"
                    binding.vpOnboarding.currentItem = 1
                }
            }

        }

    }

}
