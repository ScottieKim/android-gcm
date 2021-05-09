package com.github.scott.gcm.view.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.R
import com.github.scott.gcm.data.viewmodel.MainViewModel
import com.github.scott.gcm.databinding.ActivityMainBinding
import com.github.scott.gcm.view.adapter.MainPagerAdapter
import com.github.scott.gcm.view.fragment.LikeFragment
import com.github.scott.gcm.view.fragment.MainFragment
import com.github.scott.gcm.view.fragment.ProfileFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModel.text = "text111"

        viewModel.moveCreate.observe(this, Observer {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        })

        binding.viewModel = viewModel

        initPager()

    }

    private fun initPager() {
        viewpager_main.adapter =
            MainPagerAdapter(
                supportFragmentManager,
                listOf(
                    MainFragment(),
                    LikeFragment(),
                    ProfileFragment()
                )
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
                val tabIconColor = ContextCompat.getColor(
                    this@MainActivity,
                    R.color.blue
                )
                tab?.getIcon()?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
            }
        })
    }

}