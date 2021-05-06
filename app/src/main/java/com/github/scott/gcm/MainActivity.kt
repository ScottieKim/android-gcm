package com.github.scott.gcm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbUtil = DBUtil()
        val user = User().apply {
            email = "1231"
            password = "dlfjdf"
            name = "name"
        }
        dbUtil.insertUser(user)

        //
        val list = dbUtil.getallUsers()
        for (item in list) {
            Log.e("유저", "name: ${item.name}  email: ${item.email}  password:${item.password}")
        }

        button_list.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            // finish()
        }
    }
}