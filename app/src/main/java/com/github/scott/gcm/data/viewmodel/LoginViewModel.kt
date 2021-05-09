package com.github.scott.gcm.data.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.User
import kotlinx.android.synthetic.main.activity_signup.*

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var dbUtil: DBUtil = DBUtil()
    var moveMain = MutableLiveData<String>()
    var showToast = MutableLiveData<String>()
    var close = MainViewModel.CalledData()

    var email = ""
    var password = ""
    var name = ""

    fun onClickLogin() {
        val user: User? = dbUtil.getUserById(email)
        val pwd = user?.password ?: ""

        Log.e("LOGIN", "$email :: $password")

        if (pwd == password) {
            moveMain.value = email
        } else {
            showToast.value = "비밀번호가 틀렸습니다."
        }
    }

    fun onClickSignup() {
        val user = User().apply {
            this.email = this@LoginViewModel.email
            this.password = this@LoginViewModel.password
            this.name = this@LoginViewModel.name
        }

        dbUtil.insertUser(user)

        Toast.makeText(getApplication(), "회원가입 완료", Toast.LENGTH_SHORT).show()

        // Log
        val list = dbUtil.getallUsers()
        for (item in list) {
            Log.e("유저", "name: ${item.name}  email: ${item.email}  password:${item.password}")
        }

        close.call()
    }

    fun onClickBack() {
        close.call()
    }

}