package com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners

import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.os.Handler

class CustomOnTouchListenerImpl constructor(private val scaleByVal: Float) : View.OnTouchListener {


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scaleDown(v)
                Handler().postDelayed({
                    scaleToOriginal(v)
                }, 200)
                return true
            }
            MotionEvent.ACTION_UP -> {
                scaleToOriginal(v)
                v.performClick()
                return true
            }
        }
        return false
    }

    private fun scaleDown(v: View) {
        v.animate()
            .scaleXBy(scaleByVal)
            .setDuration(200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
        v.animate()
            .scaleYBy(scaleByVal)
            .setDuration(200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun scaleToOriginal(v: View) {
        v.animate().cancel()
        v.animate()
            .scaleX(1f).setDuration(800).setInterpolator(OvershootInterpolator()).start()
        v.animate()
            .scaleY(1f)
            .setDuration(800)
            .setInterpolator(OvershootInterpolator())
            .start()

    }
}