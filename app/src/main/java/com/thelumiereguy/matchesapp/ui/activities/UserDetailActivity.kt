package com.thelumiereguy.matchesapp.ui.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.ActivityUserDetailBinding
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.ui.viewmodels.MainViewModel
import com.thelumiereguy.matchesapp.ui.viewmodels.factory.ViewModelFactory
import com.thelumiereguy.matchesapp.utils.Utils
import com.thelumiereguy.matchesapp.utils.formatDate
import java.lang.StringBuilder
import javax.inject.Inject

class UserDetailActivity : BaseActivity() {


    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var user: UsersList.User
    private val clickListenerIml = ClickListenerIml()


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainViewModel by lazy {
        ViewModelProviders.of(
            this@UserDetailActivity,
            viewModelFactory
        ).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.getActivityComponent()?.inject(this)
        binding =
            DataBindingUtil.setContentView(this@UserDetailActivity, R.layout.activity_user_detail)

        initUI()
    }

    private fun initUI() {
        intent.extras?.let {
            user = intent.getParcelableExtra("user") as UsersList.User
            with(user) {
                setFavouriteIcon(user)
                binding.tvName.text = StringBuilder().append(this.getFullName())
                    .append(", ")
                    .append(this.dob.age.toString())
                binding.tvLocation.text = this.getFullAddress()
                binding.tvSign.text = "Dob : " + this.dob.date.formatDate()
                binding.ivUserImage.load(
                    this.picture.large
                ) {
                    crossfade(true)
                    placeholder(R.drawable.ic_user)
                    error(R.drawable.ic_user)
                }
            }
        }
        Handler().postDelayed({
            binding.cardClose.show()
        }, 500)

        binding.cardFavourite.setOnClickListener(clickListenerIml)
        binding.cardClose.setOnClickListener(clickListenerIml)
    }

    private fun setFavouriteIcon(user: UsersList.User) {
        binding.cardFavourite.setFavourite(user.favourite)
    }

    inner class ClickListenerIml : View.OnClickListener {
        override fun onClick(v: View) {
            when (v.id) {
                binding.cardClose.id -> {
                    onBackPressed()
                }
                binding.cardFavourite.id -> {
                    Utils.showRelevantSnack(
                        Utils.TYPE.FAVOURITED(
                            user.favourite,
                            user.getFullName()
                        ), findViewById(android.R.id.content)
                    )
                    user.favourite = !user.favourite
                    mainViewModel.setFavourite(user)
                    binding.cardFavourite.setFavourite(user.favourite)
                }
            }
        }

    }
}
