package com.thelumiereguy.matchesapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.data.shared_preferences.PreferencesHelperImpl
import com.thelumiereguy.matchesapp.databinding.ActivityHomeBinding
import com.thelumiereguy.matchesapp.domain.usecases.LogoutUseCase
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.ui.fragments.FavouritesFragment
import com.thelumiereguy.matchesapp.ui.fragments.HomeFragment
import com.thelumiereguy.matchesapp.ui.fragments.ProfileFragment
import com.thelumiereguy.matchesapp.ui.ui_models.FragmentNavigationDetails
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import com.thelumiereguy.matchesapp.utils.Utils
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var doubleBackToExitPressedOnce = false

    @Inject
    lateinit var preferencesHelperImpl: PreferencesHelperImpl

    @Inject
    lateinit var logoutUseCase: LogoutUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@HomeActivity, R.layout.activity_home)
        super.getActivityComponent()?.inject(this)
        connectUI()
    }

    private fun connectUI() {
        binding.bnvHome.setOnNavigationItemSelectedListener(BottomNavigationClickCallback())
        binding.bnvHome.selectedItemId = R.id.navigation_home


        /**
         * Called when logout button is pressed
         *
         * Clears the preferences and DB
         */
        binding.ibSettings.setOnClickListener {
            preferencesHelperImpl.logout()
            logoutUseCase.execute {
                onComplete {
                    goBackToLogin()
                }
                onError {
                    goBackToLogin()
                }
            }
        }
    }

    private fun goBackToLogin() {
        val intent = Intent(this, LauncherActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }



    inner class BottomNavigationClickCallback :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            var fragment: Fragment? = null
            val currentFragment =
                supportFragmentManager.findFragmentById(binding.flHomeFragmentContainer.id)
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (currentFragment !is HomeFragment) {
                        binding.toolbarTitle.text = "Matches"
                        fragment = HomeFragment()
                    }
                }
                R.id.navigation_favourites -> {
                    if (currentFragment !is FavouritesFragment) {
                        binding.toolbarTitle.text = "Favourites"
                        fragment = FavouritesFragment()
                    }
                }
                R.id.navigation_profile -> {
                    if (currentFragment !is ProfileFragment) {
                        binding.toolbarTitle.text = "Profile"
                        fragment = ProfileFragment()
                    }
                }
            }
            fragment?.let {
                val navigationArgs =
                    FragmentNavigationDetails(
                        supportFragmentManager,
                        it,
                        binding.flHomeFragmentContainer.id
                    )
                replaceFragment(navigationArgs)
                return true
            }
            return false
        }

    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            this@HomeActivity.finish()
            return
        }
        doubleBackToExitPressedOnce = true
        Utils.showInfoSnack("Please press back again to exit", findViewById(android.R.id.content))
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}
