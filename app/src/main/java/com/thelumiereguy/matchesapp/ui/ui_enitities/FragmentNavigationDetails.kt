package com.thelumiereguy.matchesapp.ui.ui_enitities

import android.widget.ImageView
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.thelumiereguy.matchesapp.R

data class FragmentNavigationDetails @JvmOverloads constructor(
    val fragmentManager: FragmentManager,
    val fragmentObj: Fragment,
    val addToBackStack: Boolean,
    val frameId: Int,
    val sharedElements: ArrayMap<String, ImageView>? = null,
    val animationSet: CustomAnimationSet? = null

)

data class CustomAnimationSet constructor(
    val enter: Int,
    val exit: Int,
    val popEnter: Int,
    val popExit: Int
) {
    companion object {
        fun getTopBottomAnimation() =
            CustomAnimationSet(
                R.anim.slide_in_from_below,
                R.anim.slide_out_to_top,
                R.anim.slide_in_from_top,
                R.anim.slide_out_to_below
            )

    }
}