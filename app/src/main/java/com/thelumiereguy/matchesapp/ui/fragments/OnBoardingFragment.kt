package com.thelumiereguy.matchesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.thelumiereguy.matchesapp.ui.ui_enitities.OnBoardingPageContent
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.FragmentOnBoardingBinding
import com.thelumiereguy.matchesapp.ui.LauncherViewModel
import com.thelumiereguy.matchesapp.ui.OnBoardingViewPagerAdapter


class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var launcherViewModel: LauncherViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_on_boarding, container, false)
        launcherViewModel = ViewModelProviders.of(requireActivity()).get(LauncherViewModel::class.java)

        connectUIComponents()
        return binding.root
    }

    private fun connectUIComponents() {
        binding.vpOnboarding.apply {
            adapter = OnBoardingViewPagerAdapter(
                OnBoardingPageContent.getOnBoardingContent()
            )
        }
        binding.btnNext.setOnClickListener(ClickListenerImpl())
    }




    private inner class ClickListenerImpl : View.OnClickListener {
        override fun onClick(v: View?) {
            when(binding.vpOnboarding.currentItem){
                1 -> {
                    launcherViewModel.showHome()
                }
                else -> {
                    binding.btnNext.text = "Start"
                    binding.vpOnboarding.currentItem = 1
                }
            }

        }

    }

}
