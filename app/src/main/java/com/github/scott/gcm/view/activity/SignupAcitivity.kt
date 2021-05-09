package com.github.scott.gcm.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.R
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.LoginViewModel
import com.github.scott.gcm.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.*

class SignupAcitivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(LoginViewModel::class.java)
        viewModel.close.observe(this, Observer { finish() })

        binding.viewModel = viewModel
    }
}