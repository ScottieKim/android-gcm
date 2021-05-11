package com.github.scott.gcm.data.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community

class CreateViewModel : ViewModel() {
    private var community = Community()
    private val dbUtil = DBUtil()

    var title = ""
    var type = ""
    var desc = ""

    var moveGallery = MainViewModel.CalledData()
    var moveMap = MainViewModel.CalledData()
    var close = MainViewModel.CalledData()
    var showToast = MutableLiveData<String>()

    fun onClickCreate() {
        community.title = title
        community.type = type
        community.description = desc

        dbUtil.insertCommunity(community)
        showToast.value = "커뮤니티 생성이 완료되었습니다."

        Log.e("COMMUNITY", "${community.title}:: ${community.type} :: ${community.img} :: ${community.lat} :: ${community.lng}")
        close.call()
    }

    fun onClickBack() {
        close.call()
    }

    fun onClickPhoto() {
        moveGallery.call()
    }

    fun setCommunityImg(img: String) {
        community.img = img
    }

    fun setCommunityOwner(email: String) {
        community.ownerEmail = email
    }

    fun setCommunityLatLng(lat: Double, lng: Double) {
        community.lat = lat
        community.lng = lng
    }

    fun onClickLocation() {
        moveMap.call()

    }
}