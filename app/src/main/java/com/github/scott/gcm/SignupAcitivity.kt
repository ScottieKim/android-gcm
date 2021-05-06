package com.github.scott.gcm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.User
import kotlinx.android.synthetic.main.activity_signup.*

class SignupAcitivity : AppCompatActivity() {
    private lateinit var dbUtil: DBUtil
    //private var dbUtil2 : DBUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        button_signup_back.setOnClickListener {
            finish()
        }


        button_signup_submit.setOnClickListener {
            if (!::dbUtil.isInitialized) {
                dbUtil = DBUtil()
            }

            val email = edittext_signup_email.text.toString()
            val password = edittext_signup_password.text.toString()
            val name = edittext_signup_name.text.toString()

            val user = User().apply {
                this.email = email
                this.password = password
                this.name = name
            }

            dbUtil.insertUser(user)
            Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
            finish()

            // Log
            val list = dbUtil.getallUsers()
            for (item in list) {
                Log.e("유저", "name: ${item.name}  email: ${item.email}  password:${item.password}")
            }
        }

    }
}