package com.github.scott.gcm.data.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.model.CommunityUser
import com.github.scott.gcm.data.model.User

class MainViewModel : ViewModel() {
    var text = "text"
    var loggedinEmail = ""

    private val dbUtil = DBUtil()
    var moveCreate = CalledData()
    var moveAlert = CalledData()
    var moveRequest = CalledData()
    var moveSearch = CalledData()
    var showSearchDate = CalledData()
    var logout = CalledData()

    var searchDate = ""
    var searchType = ""

    var isEmpty = true

    fun loggingAllUser() {
        val list = dbUtil.getAllEntities<User>()
        for (item in list) {
            Log.e("유저", "name: ${item.name}  email: ${item.email}  password:${item.password}")
        }
    }

    fun getAllCommunity(): List<Community> {
        return dbUtil.getAllEntities()
    }

    fun getAllMyCommunity(): List<Community> {
        val list = dbUtil.getCommunityUserByUser(loggedinEmail)
        val result = mutableListOf<Community>()
        for (item in list) {
            // 1. item.communityTitle로 community 검색
            // 2. result.add(검색 community)
            result.add(dbUtil.getCommunityByTitle(item.communityTitle))
        }

        isEmpty = result.isEmpty()
        return result
    }


    fun insertTestCommunity() {
        // Insert Test
        dbUtil.insertEntity(
            Community().apply {
                title = "comm3"
                description = "desc3"
                img =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"
            }
        )
        dbUtil.insertEntity(Community().apply {
            title = "comm4"
            description = "desc2"
            img =
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"

        })
        dbUtil.insertEntity(Community().apply {
            title = "comm5"
            description = "desc3"
            img =
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"

        })
    }

    fun getUser(email: String): User? {
        return dbUtil.getUserById(email)
    }


    // Click
    fun onClickCreateButton() {
        moveCreate.call()
    }

    fun onClickLogout() {
        logout.call()
    }

    fun onClickAlert() {
        moveAlert.call()
    }

    fun onClickRequest() {
        moveRequest.call()
    }

    fun onClickShowSearchDate() {
        showSearchDate.call()
    }

    fun onClickSearch() {
        moveSearch.call()
    }

    class CalledData : MutableLiveData<Boolean>() {
        fun call() {
            postValue(true)
        }
    }


}