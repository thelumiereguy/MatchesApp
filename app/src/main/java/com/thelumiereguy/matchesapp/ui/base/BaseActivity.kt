package com.thelumiereguy.matchesapp.ui.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.thelumiereguy.matchesapp.di.components.ActivityComponent
import com.thelumiereguy.matchesapp.di.components.DaggerActivityComponent
import com.thelumiereguy.matchesapp.di.modules.ContextModule
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.ui.ui_models.FragmentNavigationDetails
import com.thelumiereguy.matchesapp.utils.getClassTag

abstract class BaseActivity : AppCompatActivity() {

    private var mActivityComponent: ActivityComponent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityComponent = DaggerActivityComponent.builder()
            .contextModule(ContextModule(application))
            .build()
    }

    protected open fun getActivityComponent(): ActivityComponent? {
        return if (mActivityComponent != null) {
            mActivityComponent
        } else null
    }


    /**
     * Function which can be called from any activity, to replace a fragment
     *
     * You can customise the containerId, Custom Animations.
     * The above options are wrapped in data class
     * @see FragmentNavigationDetails
     *
     * @param navigationArgs The argument which contains all the customisable options
     */
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
        transaction.replace(navigationArgs.frameId, navigationArgs.fragmentObj).commit()
    }

}
