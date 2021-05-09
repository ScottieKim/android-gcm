package com.github.scott.gcm.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.viewmodel.LoginViewModel
import com.github.scott.gcm.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                LoginViewModel::class.java
            )
        viewModel.moveMain.observe(this, Observer {
            CommonUtil.savedUser(this, it)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })
        viewModel.showToast.observe(this, Observer {
            // ***
            Toast.makeText(this, it,     Toast.LENGTH_SHORT).show()
        })
        binding.viewModel = viewModel

        button_login_signup.setOnClickListener {
            val intent = Intent(this, SignupAcitivity::class.java)
            startActivity(intent)
        }

    }
}