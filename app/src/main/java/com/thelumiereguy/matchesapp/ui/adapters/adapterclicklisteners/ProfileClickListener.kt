package com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners

import android.widget.ImageView
import com.thelumiereguy.matchesapp.domain.enitity.UsersList

interface ProfileClickListener {
    fun onClick(user: UsersList.User,sharedImageView: ImageView)
}