package com.github.scott.gcm

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.User
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbUtil = DBUtil()

        val list = dbUtil.getallUsers()
        for (item in list) {
            Log.e("유저", "name: ${item.name}  email: ${item.email}  password:${item.password}")
        }

        initPager()

    }

    private fun initPager() {
        viewpager_main.adapter = MainPagerAdapter(
            supportFragmentManager,
            listOf(MainFragment(), LikeFragment(), ProfileFragment())
        )

        // viewpager와 하단의 Tab을 연결
        tab_main.setupWithViewPager(viewpager_main)

        // Tab 설정
        tab_main.getTabAt(0)?.setIcon(android.R.drawable.star_big_off)?.setText("Main")
        tab_main.getTabAt(1)?.setIcon(android.R.drawable.star_big_off)?.setText("Like")
        tab_main.getTabAt(2)?.setIcon(android.R.drawable.star_big_off)?.setText("Profile")

        tab_main.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor = Color.parseColor("#c8c8c8")
                tab?.getIcon()?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(this@MainActivity, R.color.blue)
                tab?.getIcon()?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
            }
        })
    }

}