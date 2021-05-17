package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class Invitation : RealmObject() {
    var hostEmail = ""
    var guestEmail = ""
    var communityTitle = ""
}