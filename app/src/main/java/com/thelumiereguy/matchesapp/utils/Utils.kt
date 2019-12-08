package com.thelumiereguy.matchesapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.snackbar.Snackbar
import com.pd.chocobar.ChocoBar
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

    fun showSuccessSnack(message:String, view: View) {
        ChocoBar.builder().setView(view)
            .setText(message)
            .setDuration(ChocoBar.LENGTH_SHORT)
            .green()
            .show()
    }

    fun showFailureSnack(message:String, view: View) {
        ChocoBar.builder().setView(view)
            .setText(message)
            .setDuration(ChocoBar.LENGTH_SHORT)
            .red()
            .show()
    }

    fun showInfoSnack(message:String, view: View) {
        ChocoBar.builder().setView(view)
            .setText(message)
            .setDuration(ChocoBar.LENGTH_SHORT)
            .orange()
            .show()
    }


    fun showRelevantSnack(type: TYPE,view: View) {
        val defaultString = " has been "
        when (type) {
            is TYPE.ACCEPTED -> {
                showSuccessSnack(
                    type.name + defaultString + "accepted",
                    view
                )
            }
            is TYPE.DECLINED -> {
                showFailureSnack(
                    type.name + defaultString + "declined",
                    view
                )
            }
            is TYPE.FAVOURITED -> {
                if (type.isFavourite!!) {
                    showFailureSnack(
                        type.name + defaultString + "removed from Favourites",
                        view
                    )
                } else {
                    showSuccessSnack(
                        type.name + defaultString + "added to Favourites",
                        view
                    )
                }
            }
        }
    }


    sealed class TYPE {

        abstract val name: String?
        abstract val isFavourite: Boolean?

        class ACCEPTED(nameArg: String) : TYPE() {
            override val isFavourite: Boolean? = false
            override val name: String? = nameArg
        }

        class DECLINED(nameArg: String) : TYPE() {
            override val isFavourite: Boolean? = false
            override val name: String? = nameArg
        }


        class FAVOURITED(isFavouriteArg: Boolean,nameArg: String) : TYPE() {
            override val isFavourite: Boolean? = isFavouriteArg
            override val name: String? = nameArg

        }
    }


}