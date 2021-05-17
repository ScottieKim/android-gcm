package com.github.scott.gcm.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.ActivityRequestmanageBinding
import com.github.scott.gcm.view.adapter.UserListAdapter

class ManageRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestmanageBinding
    private lateinit var viewModel: AlertViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_requestmanage)
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)


        // viewmodel로 옮기기
        val dbUtil = DBUtil()
        val list = dbUtil.getAllJoinRequest(null, CommonUtil.getUser(this))
        val userList = mutableListOf<User>()
        for (request in list) {
            val user = dbUtil.getUserById(request.guestEmail)
            if (user != null) {
                userList.add(user)
            }
        }

        binding.recyclerviewManage.adapter = UserListAdapter(userList, viewModel, false)
    }
}