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
import kotlin.math.roundToInt

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val dbUtil = DBUtil()

    var title: String = ""
        set(value) {
            field = value
            community = dbUtil.getCommunityByTitle(value)
        }

    var average = ""
    var count = 0

    var community: Community? = null

    var showToast = MutableLiveData<String>()

    var clickJoin = MainViewModel.CalledData()

    var clickBack = MainViewModel.CalledData()

    fun onClickBack() = clickBack.call()

    fun insertReview(title: String, email: String, rating: Float) {
        val review = Review().apply {
            this.communityTitle = title
            this.email = email
            this.review = rating
        }
        dbUtil.insertEntity(review)
    }

    // getAllReview (title)  평균값
    // 평균값, 리뷰 수
    fun averageReview() {
        val reviewList = dbUtil.getAllEntities<Review>()
        var sum = 0.0f
        for (item in reviewList) {
            sum += item.review
        }

        val avg = (sum / (reviewList.size)).toDouble()
        average = (String.format("%.2f", avg)) // 소수 두번째 자리까지 자르기
        count = reviewList.size

        println("Average : $average")
        println("Count : $count")
    }

    fun onClickJoin() = clickJoin.call()

    fun requestJoin() {

        val application = getApplication<Application>()
        val loggedinEmail = CommonUtil.getUser(application)


        if (loggedinEmail == community?.ownerEmail) {
            showToast.value = "본인이 생성한 커뮤니티에 신청할 수 없습니다."
        } else {
            val request = JoinRequest().apply {
                guestEmail = loggedinEmail
                communityTitle = title
            }
            dbUtil.insertEntity(request)
            showToast.value = "참가 신청이 완료되었습니다."
        }


        val list = dbUtil.getAllJoinRequest(community, "")
        Log.e("REQUEST", list.toString())
    }

}



