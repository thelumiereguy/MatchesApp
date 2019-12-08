package com.thelumiereguy.matchesapp.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.CustomOnTouchListenerImpl


class CustomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {


    init {
        setOnTouchListener(CustomOnTouchListenerImpl(-0.5f))
    }

    fun setFavourite(isFavourite: Boolean?) {
        if (isFavourite != null && isFavourite) {
            setImageDrawable(
                context.resources.getDrawable(
                    R.drawable.oval_background_filled_heart,
                    null
                )
            )
        } else {
            setImageDrawable(
                context.resources.getDrawable(
                    R.drawable.oval_background_empty_heart,
                    null
                )
            )

        }
    }
}