package com.thelumiereguy.matchesapp.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    val fullDateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

    val indianDateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.UK)


    fun isNetworkAvailable(context: Context): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        val cm =
            getSystemService(context, ConnectivityManager::class.java)
        val netInfo = cm!!.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals(
                    "WIFI",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedWifi = true
            if (ni.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile
    }

}