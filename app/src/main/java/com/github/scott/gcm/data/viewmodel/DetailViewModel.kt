package com.github.scott.gcm.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community

class DetailViewModel : ViewModel() {
    private val dbUtil = DBUtil()

     var title: String = ""
        set(value) {
            field = value
            community = dbUtil.getCommunityByTitle(value)
        }

    var community: Community? = null

    var showToast = MutableLiveData<String>()


    fun onClickBack() {
        return
    }


}


