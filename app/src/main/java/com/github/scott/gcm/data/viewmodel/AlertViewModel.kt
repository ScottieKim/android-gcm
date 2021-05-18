package com.github.scott.gcm.data.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Invitation
import com.github.scott.gcm.data.model.User

class AlertViewModel : ViewModel() {
    val dbUtil = DBUtil()
    var showToast = MutableLiveData<String>()
    var clickAccept = MutableLiveData<Int>()
    var setUserList = MutableLiveData<List<User>>()
    var keyword = ""
    private var list: List<User> = listOf()



    fun onClickSearch() {
        list = dbUtil.getUserListById(keyword) ?: listOf()
        setUserList.value = list
    }

    fun onClickAccept(position: Int) {
        Log.e("POSITION", position.toString())
        clickAccept.value = position
    }

    fun onClickDeny() {

    }

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
//
//    // viewmodel로 옮기기???????????
//    val dbUtil = DBUtil()
//    val list = dbUtil.getAllJoinRequest(null, CommonUtil.getUser(this))
//    val userList = mutableListOf<User>()
//    for (request in list) {
//        val user = dbUtil.getUserById(request.guestEmail)
//        if (user != null) {
//            userList.add(user)
//        }
//    }

//    binding.recyclerviewManage.adapter = UserListAdapter(userList, viewModel, false)
}
