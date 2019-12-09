package com.thelumiereguy.matchesapp.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.di.components.ActivityComponent
import com.thelumiereguy.matchesapp.di.components.DaggerActivityComponent
import com.thelumiereguy.matchesapp.di.modules.ContextModule
import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.activities.UserDetailActivity
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.ProfileClickListener
import com.thelumiereguy.matchesapp.utils.getClassTag
import java.lang.Exception


abstract class BaseFragment : Fragment(), ProfileClickListener {

    private var mActivityComponent: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityComponent = DaggerActivityComponent.builder()
            .contextModule(ContextModule(requireActivity().application))
            .build()
    }

    protected open fun getActivityComponent(): ActivityComponent? {
        return if (mActivityComponent != null) {
            mActivityComponent
        } else null
    }


    /**
     * Handles the click if a user's profile is clicked
     *
     * Since this can be called from 2 fragments, it has been added to the Base
     *
     * @param user The object of the user to be sent
     *
     * @param sharedImageView The object of the ImageView which is shared among the Activities
     *
     */
    override fun onClick(user: UsersList.User, sharedImageView: ImageView) {
        try {
            val options: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    sharedImageView as View,
                    requireContext().getString(R.string.user_image_transition)
                )
            val intent = Intent(
                requireActivity(),
                UserDetailActivity::class.java
            )
            intent.putExtra("user", user)

            startActivity(
                intent,
                options.toBundle()
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
