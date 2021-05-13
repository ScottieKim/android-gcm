package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class Review : RealmObject() {
    var email = ""
    var communityTitle = ""
    var review = 0f
}