package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class Review : RealmObject() {
    var email = ""
    var communityTitle = ""
    var review = 0f

    companion object {
        val FIELD_REVIEW_EMAIL = "email"
        val FIELD_REVIEW_COMMUNITY_TITLE = "communityTitle"
    }
}