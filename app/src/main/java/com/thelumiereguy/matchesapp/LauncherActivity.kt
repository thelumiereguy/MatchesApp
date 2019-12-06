package com.thelumiereguy.matchesapp

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.thelumiereguy.matchesapp.CustomAnimationSet.Companion.getTopBottomAnimation

class LauncherActivity : BaseActivity() {

    private lateinit var flFragmentContainer: FrameLayout
    private lateinit var launcherViewModel: LauncherViewModel


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
        window.allowEnterTransitionOverlap = false
        window.allowReturnTransitionOverlap = false
        flFragmentContainer = findViewById(R.id.fl_launcher_fragment_container)
        launcherViewModel = ViewModelProviders.of(this).get(LauncherViewModel::class.java)
    }


    private fun showSplashFragment() {
        val navigationArgs = FragmentNavigationDetails(
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
        val navigationArgs = FragmentNavigationDetails(
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



