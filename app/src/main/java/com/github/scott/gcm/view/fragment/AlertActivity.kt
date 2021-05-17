package com.github.scott.gcm.view.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.ActivityAlertBinding
import com.github.scott.gcm.view.adapter.MainPagerAdapter

class AlertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlertBinding
    private lateinit var viewModel: AlertViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alert)
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)
        viewModel.clickAccept.observe(this, Observer { position ->
            CommonUtil.showDialog(context = this,
                message = resources.getString(R.string.invite_dialog_message),
                positiveTask = {
                    viewModel.acceptInvitation(CommonUtil.getUser(this), position)
                    Toast.makeText(this, "Click Accept", Toast.LENGTH_SHORT).show()
                }, negativeTask = {
                    Toast.makeText(this, "Click Deny", Toast.LENGTH_SHORT).show()
                })
        })
        viewModel.showToast.observe(
            this,
            Observer { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })
        setPager()
    }

    private fun setPager() {
        val invitationFragment = InvitationFragment()

        binding.viewpagerAlert.adapter =
            MainPagerAdapter(
                supportFragmentManager,
                listOf(
                    invitationFragment,
                    InvitationFragment()
                )
            )

        binding.tabAlert.addTab(binding.tabAlert.newTab().setText("Invitation"))
        binding.tabAlert.addTab(binding.tabAlert.newTab().setText("Notification"))
        binding.tabAlert.setupWithViewPager(binding.viewpagerAlert)

    }
}