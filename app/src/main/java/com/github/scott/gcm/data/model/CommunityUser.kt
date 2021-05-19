package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class CommunityUser : RealmObject() {
    var email = ""
    var communityTitle = ""

    companion object {
        val FIELD_COMMUNITY_USER_EMAIL = "email"
        val FIELD_COMMUNITY_TITLE = "communityTitle"
    }
}