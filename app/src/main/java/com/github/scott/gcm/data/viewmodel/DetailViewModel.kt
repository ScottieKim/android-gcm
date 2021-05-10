package com.github.scott.gcm.data.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.model.User

class DetailViewModel:ViewModel() {
    private var title = ""
    private val dbUtil = DBUtil()
    var community: Community? = dbUtil.getCommunityByTitle(title)



    var showToast = MutableLiveData<String>()


    fun onClickDetail() {
        title = community.title
        location = community.location
        description = community.description
        ownerEmail = community.ownerEmail
        community.img
        community.type

        return
    }

    fun getCommunityByTitle():Community{
        return dbUtil.getCommunityByTitle(title)
    }

    fun setCommunityImg(img: String) {
        community.img = img
    }

    fun setCommunityOwner(email: String) {
        community.ownerEmail = email
    }



}


