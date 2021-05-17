package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class JoinRequest : RealmObject() {
    var guestEmail = ""
    var communityTitle = ""
}