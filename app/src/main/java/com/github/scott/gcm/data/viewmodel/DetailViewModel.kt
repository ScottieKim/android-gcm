package com.github.scott.gcm.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.model.JoinRequest
import com.github.scott.gcm.data.model.Review

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val dbUtil = DBUtil()


    var title: String = ""
        set(value) {
            field = value
            community = dbUtil.getCommunityByTitle(value)
        }

    var community: Community? = null

    var showToast = MutableLiveData<String>()

    var clickJoin = MainViewModel.CalledData()


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


    fun averageReview() {
        val reviewList = dbUtil.getAllReviews()
        var sum = 0.0f
        for (item in reviewList) {
            sum += item.review
        }

        val average = sum / reviewList.size
        val count = reviewList.size

        println("Average : $average")
        println("Count : $count")

    }

    fun onClickJoin() = clickJoin.call()

    fun requestJoin() {

        val application = getApplication<Application>()
        val loggedinEmail = CommonUtil.getUser(application)


        if(loggedinEmail == community?.ownerEmail){
            showToast.value = "본인이 생성한 커뮤니티에 신청할 수 없습니다."
        } else {
            val request = JoinRequest().apply {
                guestEmail = loggedinEmail
                communityTitle = title
            }
            dbUtil.insertJoinRequest(request)
            showToast.value = "참가 신청이 완료되었습니다."
        }


        val list = dbUtil.getAllJoinRequest(community, "")
        Log.e("REQUEST", list.toString())
    }

}



