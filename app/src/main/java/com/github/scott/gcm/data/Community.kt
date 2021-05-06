package com.github.scott.gcm.data

import io.realm.RealmObject

open class Community : RealmObject() {
    var title = ""

    var type = ""

    var location = 0f

    var description = ""

    var img = ""
}