package com.thelumiereguy.matchesapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.thelumiereguy.matchesapp.*
import com.thelumiereguy.matchesapp.ui.ui_enitities.CustomAnimationSet.Companion.getTopBottomAnimation
import com.thelumiereguy.matchesapp.ui.LauncherViewModel
import com.thelumiereguy.matchesapp.ui.fragments.OnBoardingFragment
import com.thelumiereguy.matchesapp.ui.fragments.SplashFragment
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.ui.factory.ViewModelFactory
import com.thelumiereguy.matchesapp.ui.ui_enitities.FragmentNavigationDetails
import javax.inject.Inject

class LauncherActivity : BaseActivity() {




    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val launcherViewModel: LauncherViewModel by lazy { ViewModelProviders.of(this,viewModelFactory).get(LauncherViewModel::class.java) }

    private lateinit var flFragmentContainer: FrameLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initUIComponents()
        startObservingState()
        showSplashFragment()
    }

    private fun startObservingState() {
        launcherViewModel.getState().observe(this, Observer { observable ->
            observable?.let {
                when (it) {
                    is LauncherViewModel.LauncherState.OnBoarding -> {
                        showOnBoarding()
                    }
                    is LauncherViewModel.LauncherState.Home -> {
                        startHome()
                    }
                    else -> {

                    }
                }
            }

        })
    }

    private fun initUIComponents() {
        super.getActivityComponent()?.inject(this)
        window.allowEnterTransitionOverlap = false
        window.allowReturnTransitionOverlap = false
        flFragmentContainer = findViewById(R.id.fl_launcher_fragment_container)
    }


    private fun showSplashFragment() {
        val navigationArgs =
            FragmentNavigationDetails(
                supportFragmentManager,
                SplashFragment(),
                false,
                flFragmentContainer.id
            )
        super.replaceFragment(navigationArgs)
    }


    /**
     * Replaces SplashFragment with OnBoardingFragment on 1st Launch
     */
    private fun showOnBoarding() {
        val navigationArgs =
            FragmentNavigationDetails(
                supportFragmentManager,
                OnBoardingFragment(),
                false,
                flFragmentContainer.id,
                animationSet = getTopBottomAnimation()
            )
        super.replaceFragment(navigationArgs)
    }


    /**
     * Starts the Home Activity instead of OnBoarding
     */
    private fun startHome() {
        startActivity(
            Intent(this@LauncherActivity, HomeActivity::class.java)
        )
        finish()

    }


}



