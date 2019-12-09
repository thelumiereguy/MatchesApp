package com.thelumiereguy.matchesapp.utils

import android.util.Log
import com.thelumiereguy.matchesapp.utils.Utils.fullDateFormat
import com.thelumiereguy.matchesapp.utils.Utils.indianDateFormat


fun Any.getClassTag(): String = this.javaClass.simpleName

fun Any.getMethodTag(): String =
    getClassTag() + object : Any() {}.javaClass.enclosingMethod?.name



fun String.formatDate(): String {
    return try {
        val date = fullDateFormat.parse(this)
        Log.d(getMethodTag(), indianDateFormat.format(date!!))
        indianDateFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}


