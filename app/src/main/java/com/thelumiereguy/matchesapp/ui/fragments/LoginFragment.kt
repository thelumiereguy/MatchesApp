package com.thelumiereguy.matchesapp.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.data.shared_preferences.PreferencesHelperImpl
import com.thelumiereguy.matchesapp.ui.adapters.OnBoardingViewPagerAdapter
import com.thelumiereguy.matchesapp.ui.base.BaseFragment
import com.thelumiereguy.matchesapp.ui.ui_models.OnBoardingPageContent
import com.thelumiereguy.matchesapp.ui.viewmodels.LauncherViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import javax.inject.Inject


class LoginFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var  preferencesHelperImpl: PreferencesHelperImpl

    private lateinit var launcherViewModel: LauncherViewModel

    private lateinit var registerBtn: MaterialButton
    private lateinit var loginButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        connectUIComponents(view)
        return view
    }

    private fun connectUIComponents(view: View) {
        super.getActivityComponent()?.inject(this)

        loginButton = view.findViewById(R.id.btn_login)
        registerBtn = view.findViewById(R.id.btn_register)

        launcherViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(
            LauncherViewModel::class.java
        )
        registerBtn.setOnClickListener(ClickListenerImpl())
        loginButton.setOnClickListener(ClickListenerImpl())
    }

    inner class ClickListenerImpl : View.OnClickListener {


        /**
         * Sets the flag of loggedIn in Shared Preferences, to start a logged in session
         *
         *
         */
        override fun onClick(v: View?) {
            preferencesHelperImpl.setLoggedIn()
            launcherViewModel.showHome()

        }

    }
}
