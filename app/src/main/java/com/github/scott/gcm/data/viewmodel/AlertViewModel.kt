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
    var clickAccept = MutableLiveData<Int>()
    var clickDeny = MutableLiveData<Int>()
    var setUserList = MutableLiveData<MutableList<User>>()
    var keyword = ""

    fun onClickSearch() {
        list = dbUtil.getUserListById(keyword)?.toMutableList() ?: mutableListOf()
        setUserList.value = list
    }

    fun onClickManageRequest(position: Int, isAccepted: Boolean) {
        // Log.e("POSITION", position.toString())
        if(isAccepted) clickAccept.value = position
        else clickDeny.value = position
    }

    // 초대 승낙
    fun acceptInvitation(loggedinEmail: String, position: Int) {
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
    fun manageRequest(user: User, isAccepted: Boolean) {
        val hostEmail = CommonUtil.getUser(getApplication<Application>().applicationContext)
        val community = dbUtil.getCommunityByUser(hostEmail)

        // Log
//        Log.e("Join Request 1 ", " ")
//        for (item in dbUtil.getAllJoinRequest(community, hostEmail)) {
//            Log.e(">>> ", "${item.communityTitle}   ${item.guestEmail}")
//        }


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
//            Log.e("Community User 2 ", " ")
//            for (item in dbUtil.getAllEntities<CommunityUser>()) {
//                Log.e(">>> ", "${item.communityTitle}   ${item.email}")
//            }


            // 2. delete JoinRequest
            val joinRequest = dbUtil.getJoinRequestByUser(user.email, community.title)
            dbUtil.deleteEntity(joinRequest!!)


            // Log
//            Log.e("Join Request 3 ", " ")
//            for (item in dbUtil.getAllJoinRequest(community, hostEmail)) {
//                Log.e(">>> ", "${item.communityTitle}   ${item.guestEmail}")
//            }
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
}
