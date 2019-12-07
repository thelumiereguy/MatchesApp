package com.thelumiereguy.matchesapp.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.thelumiereguy.matchesapp.di.components.ActivityComponent
import com.thelumiereguy.matchesapp.di.components.DaggerActivityComponent
import com.thelumiereguy.matchesapp.di.modules.ContextModule
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.ui.ui_models.FragmentNavigationDetails
import com.thelumiereguy.matchesapp.utils.getClassTag
import java.lang.StringBuilder

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

    fun showErrorSnack(errorModel: ErrorModel) {
        val errorMessage = StringBuilder()
            .append(errorModel.errorCode)
            .append(" ")
            .append(errorModel.message)
        Log.w(getClassTag(), errorMessage.toString())
        Snackbar.make(
            findViewById(android.R.id.content),
            errorMessage.toString(),
            Snackbar.LENGTH_LONG
        ).show()
    }

}
