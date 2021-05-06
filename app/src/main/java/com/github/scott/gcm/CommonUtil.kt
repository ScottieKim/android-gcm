package com.github.scott.gcm

import android.app.Activity
import android.content.Context

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
}