package com.github.scott.gcm.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.model.User

class MainViewModel : ViewModel() {
    var text = "text"
    private val dbUtil = DBUtil()
    var moveCreate = CalledData()

    fun loggingAllUser() {
        val list = dbUtil.getallUsers()
        for (item in list) {
            Log.e("유저", "name: ${item.name}  email: ${item.email}  password:${item.password}")
        }
    }

    fun getAllCommunity(): List<Community> {
        return dbUtil.getAllCommunity()
    }

    fun insertTestCommunity() {
        // Insert Test
        dbUtil.insertCommunity(Community().apply {
            title = "comm3"
            description = "desc3"
            img =
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"
        })
        dbUtil.insertCommunity(Community().apply {
            title = "comm4"
            description = "desc2"
            img =
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"

        })
        dbUtil.insertCommunity(Community().apply {
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


    class CalledData : MutableLiveData<Boolean>() {
        fun call() {
            this.postValue(true)
        }
    }
}