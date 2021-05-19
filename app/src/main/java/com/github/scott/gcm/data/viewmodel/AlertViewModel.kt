package com.github.scott.gcm.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.CommunityUser
import com.github.scott.gcm.data.model.Invitation
import com.github.scott.gcm.data.model.JoinRequest
import com.github.scott.gcm.data.model.User

class AlertViewModel(application: Application) : AndroidViewModel(application) {
    private val dbUtil = DBUtil()
    private var list: MutableList<User> = mutableListOf()

    var showToast = MutableLiveData<String>()
    var clickInvitation = MutableLiveData<Int>()
    var clickAccept = MutableLiveData<Int>()
    var clickDeny = MutableLiveData<Int>()
    var clickBack = MainViewModel.CalledData()
    var setUserList = MutableLiveData<MutableList<User>>()
    var keyword = ""

    var notificationList: MutableList<Invitation> = mutableListOf()
    var clickedNotification: Invitation? = null

    fun onClickSearch() {
        list = dbUtil.getUserListById(keyword)?.toMutableList() ?: mutableListOf()
        setUserList.value = list
    }

    fun onClickBack(){
        clickBack.call()
    }

    fun onClickManageRequest(position: Int, isAccepted: Boolean) {
        // Log.e("POSITION", position.toString())
        if (isAccepted) clickAccept.value = position
        else clickDeny.value = position

        // click item
        if (notificationList.isNotEmpty()) {
            clickedNotification = notificationList[position]
        }
    }

    fun onClickInvitation(position: Int) {
        clickInvitation.value = position
    }

    // 초대
    fun sendInvitation(position: Int, isAccepted: Boolean) {
        val context = getApplication<Application>().applicationContext
        val loggedinEmail = CommonUtil.getUser(context)

        val community = dbUtil.getCommunityByUser(loggedinEmail)
        if (community != null) {
            val invitation = Invitation().apply {
                hostEmail = loggedinEmail
                guestEmail = list[position].email
                communityTitle = community.title
            }
            dbUtil.insertEntity(invitation)
            showToast.value = "초대가 완료되었습니다."
        } else {
            showToast.value = "초대할 수 있는 커뮤니티가 없습니다."
        }
    }

    // 신청 승낙
    // isRequest : Join Request 인지 여부
    fun manageRequest(user: User, isAccepted: Boolean, isRequest: Boolean) {
        val hostEmail = CommonUtil.getUser(getApplication<Application>().applicationContext)
        val community = if (isRequest) dbUtil.getCommunityByUser(hostEmail) else {
            val commTitle = clickedNotification?.communityTitle
            if (commTitle != null) {
                dbUtil.getCommunityByTitle(commTitle)
            } else {
                null
            }
        }

        // Log
        Log.e("Join Request 1 ", " ")
        for (item in dbUtil.getAllJoinRequest(community, hostEmail)) {
            Log.e(">>> ", "${item.communityTitle}   ${item.guestEmail}")
        }

        if (community != null) {
            if (isAccepted) {
                // 1. insert CommunityUser
                val communityUser = CommunityUser().apply {
                    email = user.email
                    communityTitle = community.title
                }
                dbUtil.insertEntity(communityUser)
            }

            // Log
            Log.e("Community User 2 ", " ")
            for (item in dbUtil.getAllEntities<CommunityUser>()) {
                Log.e(">>> ", "${item.communityTitle}   ${item.email}")
            }


            // 2. delete JoinRequest
            if (isRequest) {
                val joinRequest = dbUtil.getJoinRequestByUser(user.email, community.title)
                dbUtil.deleteEntity(joinRequest!!)
            } else {
                // Invitation
                val invitation = dbUtil.getInvitationByUser(user.email, community.title)
                dbUtil.deleteEntity(invitation)
            }


            // Log
            Log.e("Join Request 3 ", " ")
            for (item in dbUtil.getAllJoinRequest(community, hostEmail)) {
                Log.e(">>> ", "${item.communityTitle}   ${item.guestEmail}")
            }

            // Log
            Log.e("Invitation3 ", " ")
            for (item in dbUtil.getAllInvitationByUser(user.email)) {
                Log.e(">>> ", "${item.communityTitle}   ${item.guestEmail}")
            }

        }
    }

    // Manage Request List 구성
    fun getUserList(): List<User> {
        val context = getApplication<Application>().applicationContext
        val list = dbUtil.getAllJoinRequest(null, CommonUtil.getUser(context))
        val userList = mutableListOf<User>()
        for (request in list) {
            val user = dbUtil.getUserById(request.guestEmail)
            if (user != null) {
                userList.add(user)
            }
        }
        return userList
    }

    // Notification List 구성
    fun getInvitationList(): List<Invitation> {
        val context = getApplication<Application>().applicationContext
        val email = CommonUtil.getUser(context)
        return dbUtil.getAllInvitationByUser(email).toMutableList()
    }
}
