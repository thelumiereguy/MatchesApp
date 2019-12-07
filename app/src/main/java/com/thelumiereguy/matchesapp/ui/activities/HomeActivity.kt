package com.thelumiereguy.matchesapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.ActivityHomeBinding
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.ui.fragments.FavouritesFragment
import com.thelumiereguy.matchesapp.ui.fragments.HomeFragment
import com.thelumiereguy.matchesapp.ui.fragments.ProfileFragment
import com.thelumiereguy.matchesapp.ui.ui_models.FragmentNavigationDetails

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@HomeActivity, R.layout.activity_home)
        binding.bnvHome.setOnNavigationItemSelectedListener(BottomNavigationClickCallback())
        binding.bnvHome.selectedItemId = R.id.navigation_home
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
                        binding.toolbarTitle.text  = "Favourites"
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
                        false,
                        binding.flHomeFragmentContainer.id
                    )
                replaceFragment(navigationArgs)
                return true
            }
            return false
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
