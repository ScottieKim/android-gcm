package com.github.scott.gcm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.scott.gcm.data.DBUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){
    private lateinit var dbUtil: DBUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbUtil = DBUtil()


        button_login_submit.setOnClickListener {
            val email = edittext_login_email.text.toString()
            val password = edittext_login_password.text.toString()
            val user = dbUtil.getUserById(email)

            if (user.password == password){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        button_login_signup.setOnClickListener {
            val intent = Intent(this, SignupAcitivity::class.java)
            startActivity(intent)
        }

    }
}