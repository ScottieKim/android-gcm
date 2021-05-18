package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class Invitation : RealmObject() {
    var hostEmail = ""
    var guestEmail = ""
    var communityTitle = ""


    companion object {
        val FIELD_INVITATION_HOST_EMAIL = "hostEmail"
        val FIELD_INVITATION_GUEST_EMAIL = "guestEmail"
        val FIELD_INVITATION_COMMUNITY_TITLE = "communityTitle"
    }
}