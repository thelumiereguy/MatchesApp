package com.thelumiereguy.matchesapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import coil.api.load
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.ActivityUserDetailBinding
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.base.BaseActivity
import com.thelumiereguy.matchesapp.utils.formatDate

class UserDetailActivity : BaseActivity() {


    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var user: UsersList.User
    val clickListenerIml = ClickListenerIml()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this@UserDetailActivity, R.layout.activity_user_detail)
        initUI()
    }

    private fun initUI() {
        intent.extras?.let {
            user = intent.getParcelableExtra("user") as UsersList.User
            with(user) {
                binding.tvName.text =
                    this.name.first + " " + this.name.last + " , " + this.dob.age.toString()
                binding.tvLocation.text = this.location.city + " " + this.location.state
                binding.tvSign.text = this.dob.date.formatDate()
                binding.ivUserImage.load(
                    this.picture.large
                )
            }
        }
        binding.cardClose.setOnClickListener(clickListenerIml)
        binding.cardFavourite.setOnClickListener(clickListenerIml)
    }

    inner class ClickListenerIml : View.OnClickListener {
        override fun onClick(v: View) {
            when (v.id) {
                binding.cardClose.id -> {
                    onBackPressed()
                }
                binding.cardFavourite.id -> {

                }
            }
        }

    }
}
