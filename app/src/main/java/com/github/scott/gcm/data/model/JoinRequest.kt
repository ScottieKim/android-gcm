package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class JoinRequest : RealmObject() {
    var guestEmail = ""
    var communityTitle = ""

    companion object {
        val FIELD_JOIN_REQUEST_GUEST_EMAIL = "guestEmail"
        val FIELD_JOIN_REQUEST_COMMUNITY_TITLE = "communityTitle"
    }
}