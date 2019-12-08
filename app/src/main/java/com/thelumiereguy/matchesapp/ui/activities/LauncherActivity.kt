package com.thelumiereguy.matchesapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.thelumiereguy.matchesapp.*
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.ui.ui_models.CustomAnimationSet.Companion.getTopBottomAnimation
import com.thelumiereguy.matchesapp.ui.viewmodels.LauncherViewModel
import com.thelumiereguy.matchesapp.ui.fragments.OnBoardingFragment
import com.thelumiereguy.matchesapp.ui.fragments.SplashFragment
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.ui.fragments.LoginFragment
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import com.thelumiereguy.matchesapp.ui.ui_models.FragmentNavigationDetails
import com.thelumiereguy.matchesapp.utils.getClassTag
import com.thelumiereguy.matchesapp.utils.getMethodTag
import java.lang.StringBuilder
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class LauncherActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val launcherViewModel: LauncherViewModel by lazy {
        ViewModelProviders.of(
            this@LauncherActivity,
            viewModelFactory
        ).get(LauncherViewModel::class.java)
    }

    private lateinit var flFragmentContainer: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initUIComponents()
        startObservingState()
        if (savedInstanceState == null)
            showSplashFragment()
    }

    private fun startObservingState() {

        launcherViewModel.getState().observe(this@LauncherActivity, Observer { observable ->
            observable?.let {
                when (it) {
                    is LauncherViewModel.LauncherState.OnBoarding -> {
                        showOnBoarding()
                    }
                    is LauncherViewModel.LauncherState.Home -> {
                        startHome()
                    }
                    is LauncherViewModel.LauncherState.Login -> {
                        showLogin()
                    }
                }
            }
        })

        launcherViewModel.error.observe(this@LauncherActivity, Observer { observable ->
            observable?.let {
                when (it.exception) {
                    is UnknownHostException -> {

                    }
                    is ConnectException -> {

                    }
                    else -> {
                        showErrorSnack(it)
                    }
                }
            }
        })
    }

    private fun showLogin() {
        val navigationArgs =
            FragmentNavigationDetails(
                supportFragmentManager,
                LoginFragment(),
                flFragmentContainer.id,
                animationSet = getTopBottomAnimation()
            )
        super.replaceFragment(navigationArgs)
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



