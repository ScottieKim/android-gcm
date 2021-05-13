package com.github.scott.gcm.data.model

import io.realm.RealmObject

open class Community : RealmObject() {
    var title = ""

    var type = ""

    var lat = 0.0
    var lng = 0.0

    var description = ""

    var img = ""

    var ownerEmail = ""

    var startDate = ""

    var endDate = ""
}