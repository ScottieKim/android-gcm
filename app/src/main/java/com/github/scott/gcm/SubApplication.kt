package com.github.scott.gcm

import android.app.Application
import io.realm.Realm

class SubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
    }

    init {

    }
}