package com.github.scott.gcm.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.model.Review

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

    fun insertReview(title: String, email: String, rating: Float) {
        val review = Review().apply {
            this.communityTitle = title
            this.email = email
            this.review = rating
        }
        dbUtil.insertReview(review)
    }

    // getAllReview (title)  평균값
    // 평균값, 리뷰 수
}


