package com.github.scott.gcm

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object CommonUtil {
    val SP_USER = "user"
    val EMAIL = "email"

    fun savedUser(activity: Activity, email: String) {
        val sharedPref = activity.getSharedPreferences(SP_USER, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(EMAIL, email)
        editor.commit()
    }

    fun getUser(activity: Activity): String {
        return activity.getSharedPreferences(SP_USER, Context.MODE_PRIVATE).getString(EMAIL, "")
            ?: ""
    }

    fun getUser(context: Context): String {
        return context.getSharedPreferences(SP_USER, Context.MODE_PRIVATE).getString(EMAIL, "")
            ?: ""
    }

    fun showDialog(
        context: Context,
        message: String,
        positiveTask: () -> Unit,
        negativeTask: () -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
            .setPositiveButton("Yes") { dialog, id ->
                positiveTask()
            }
            .setNegativeButton("No") { dialog, id ->
                negativeTask()
            }
        // Create the AlertDialog object and return it
        builder.create()
        builder.show()
    }

    interface DialogListener {
        fun positiveTask()
    }
}