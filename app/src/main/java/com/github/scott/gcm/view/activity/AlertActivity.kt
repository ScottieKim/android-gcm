package com.github.scott.gcm.view.activity

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.ActivityAlertBinding
import com.github.scott.gcm.view.adapter.MainPagerAdapter
import com.github.scott.gcm.view.adapter.TabPagerAdapter
import com.github.scott.gcm.view.adapter.UserListAdapter
import com.github.scott.gcm.view.fragment.InvitationFragment
import com.github.scott.gcm.view.fragment.NotificationFragment

class AlertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlertBinding
    private lateinit var viewModel: AlertViewModel
    private var indicatorWidth = 0
    private val invitationFragment = InvitationFragment()
    private val notification = NotificationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
    }

    private fun initBinding() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_alert)
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)
        viewModel.clickBack.observe(this, Observer { finish() })
        viewModel.clickInvitation.observe(this, Observer { position ->
            CommonUtil.showDialog(context = this,
                message = resources.getString(R.string.invite_dialog_message),
                positiveTask = {
                    viewModel.sendInvitation(position, true)
                    Toast.makeText(this, "Click Accept", Toast.LENGTH_SHORT).show()
                }, negativeTask = {
                    Toast.makeText(this, "Click Deny", Toast.LENGTH_SHORT).show()
                })
        })
        viewModel.clickAccept.observe(this, Observer { position ->
            CommonUtil.showDialog(context = this,
                message = resources.getString(R.string.managerequest_dialog_message_accept),
                positiveTask = {
                    val user = User().apply {
                        email = notification.getListItem(position).guestEmail
                    }
                    viewModel.manageRequest(user, true, false)
                    notification.deleteListItem(position)
                    Toast.makeText(this, "Click Accept", Toast.LENGTH_SHORT).show()
                }, negativeTask = {
                    val user = User().apply {
                        email = notification.getListItem(position).guestEmail
                    }
                    viewModel.manageRequest(user, false, false)
                    notification.deleteListItem(position)
                    Toast.makeText(this, "Click Deny", Toast.LENGTH_SHORT).show()
                })
        })
        viewModel.showToast.observe(
            this,
            Observer { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })
        setPager()

        binding.viewModel = viewModel
    }

    private fun setPager() {
        val margin = 20

        binding.viewpagerAlert.adapter =
            TabPagerAdapter(
                supportFragmentManager,
                listOf(
                    invitationFragment,
                    notification
                ), listOf("Invitation", "Notification")
            )
        binding.viewpagerAlert.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                posOffsetPixels: Int
            ) {
                // 탭 선택 시 indicator width 변경
                val params = binding.indicator.layoutParams as FrameLayout.LayoutParams

                // 탭 width 만큼 margin
                val translationOffset = (positionOffset + position) * indicatorWidth
                params.leftMargin = translationOffset.toInt() + margin

                if (binding.tabAlert.selectedTabPosition == 1) {
                    params.rightMargin = translationOffset.toInt() - margin
                }

                binding.indicator.layoutParams = params
            }

            override fun onPageSelected(position: Int) {
            }
        })

        binding.tabAlert.setupWithViewPager(binding.viewpagerAlert)

        // indicator 초기화
        binding.tabAlert.post {
            indicatorWidth = binding.tabAlert.width / binding.tabAlert.tabCount - margin

            val indicatorParams = binding.indicator.layoutParams as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            binding.indicator.layoutParams = indicatorParams
        }

    }
}