package com.thelumiereguy.matchesapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.thelumiereguy.matchesapp.di.components.ActivityComponent
import com.thelumiereguy.matchesapp.di.components.DaggerActivityComponent
import com.thelumiereguy.matchesapp.ui.ui_enitities.FragmentNavigationDetails

abstract class BaseActivity : AppCompatActivity() {

    private var mActivityComponent: ActivityComponent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityComponent = DaggerActivityComponent.builder()
            .build()
    }

    protected open fun getActivityComponent(): ActivityComponent? {
        return if (mActivityComponent != null) {
            mActivityComponent
        } else null
    }

    fun replaceFragment(
        navigationArgs: FragmentNavigationDetails
    ) {
        val transaction: FragmentTransaction = navigationArgs.fragmentManager.beginTransaction()
        navigationArgs.animationSet?.let {
            transaction.setCustomAnimations(
                it.enter,
                it.exit,
                it.popEnter,
                it.popExit
            )
        }
        navigationArgs.sharedElements?.let {
            transaction.apply {
                for ((key, value) in it) {
                    addSharedElement(value, key)
                }
            }
        }
        if (navigationArgs.addToBackStack) {
            transaction.addToBackStack(navigationArgs.fragmentObj.javaClass.simpleName)
        }
        transaction.replace(navigationArgs.frameId, navigationArgs.fragmentObj).commit()
    }
}
