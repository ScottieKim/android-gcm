package com.github.scott.gcm.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var email = ""

    var password = ""

    var name = ""

}