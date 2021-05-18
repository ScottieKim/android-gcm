package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class CommunityUser : RealmObject() {
    var email = ""
    var communityTitle = ""
}