package com.thelumiereguy.matchesapp

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

abstract class BaseActivity : AppCompatActivity() {

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
