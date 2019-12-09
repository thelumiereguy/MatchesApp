package com.thelumiereguy.matchesapp.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.CustomOnTouchListenerImpl
import com.thelumiereguy.matchesapp.utils.Constants


class CustomMaterialButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {


    init {
        setOnTouchListener(CustomOnTouchListenerImpl(-0.5f))
    }

    fun setStatus(status: String?) {
        when (status) {
            Constants.ACCEPTED -> {
                visibility = View.VISIBLE
                setBackgroundColor(context.resources.getColor(R.color.card_background_green))
                text = context.getString(R.string.member_accepted)
            }
            Constants.DECLINED -> {
                visibility = View.VISIBLE
                setBackgroundColor(context.resources.getColor(R.color.card_background_red))
                text = context.getString(R.string.member_declined)

            }
            else -> {
                visibility = View.GONE
            }
        }
        invalidate()
    }


}