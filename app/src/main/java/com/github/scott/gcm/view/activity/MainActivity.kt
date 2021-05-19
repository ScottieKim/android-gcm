package com.github.scott.gcm.view.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.viewmodel.MainViewModel
import com.github.scott.gcm.databinding.ActivityMainBinding
import com.github.scott.gcm.view.adapter.MainPagerAdapter
import com.github.scott.gcm.view.fragment.MainFragment
import com.github.scott.gcm.view.fragment.MyCommunityFragment
import com.github.scott.gcm.view.fragment.ProfileFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mainFragment: MainFragment
    private val CREATE_COMMUNITY = 10002


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loggedinEmail = CommonUtil.getUser(this)

        viewModel.moveRequest.observe(this, Observer {
            val intent = Intent(this, ManageRequestActivity::class.java)
            startActivity(intent)
        })
        viewModel.moveAlert.observe(this, Observer {
            val intent = Intent(this, AlertActivity::class.java)
            startActivity(intent)
        })
        viewModel.moveCreate.observe(this, Observer {
            val intent = Intent(this, CreateActivity::class.java)
            startActivityForResult(intent, CREATE_COMMUNITY)
        })
        viewModel.logout.observe(this, Observer {
            // 로그아웃
            CommonUtil.savedUser(this, "")

            val options =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                    getString(
                        R.string.default_web_client_id
                    )
                ).requestEmail().build()
            val client = GoogleSignIn.getClient(this, options)
            client.signOut()

            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()

            // 구글 액세스 권한 해제
            // client.revokeAccess()

            // 로그아웃 후 로그인 화면으로 이동
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
        viewModel.showSearchDate.observe(this, Observer { showDatePicker() })
        viewModel.moveSearch.observe(this, Observer {
            val intent = Intent(this, SearchActivity::class.java).apply {
                putExtra("date", viewModel.searchDate)
                putExtra("type", viewModel.searchType)
            }
            startActivity(intent)
        })

        binding.viewModel = viewModel

        initPager()
    }

    private fun initPager() {
        mainFragment = MainFragment()

        viewpager_main.adapter =
            MainPagerAdapter(
                supportFragmentManager,
                listOf(
                    mainFragment,
                    MyCommunityFragment(),
                    ProfileFragment()
                )
            )

        // viewpager와 하단의 Tab을 연결
        tab_main.setupWithViewPager(viewpager_main)

        // Tab 설정
        tab_main.getTabAt(0)?.setIcon(android.R.drawable.star_big_off)?.setText("Main")
        tab_main.getTabAt(1)?.setIcon(android.R.drawable.star_big_off)?.setText("MyCommunity")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_COMMUNITY) {
            mainFragment.refreshList()
        }
    }

    private fun showDatePicker() {
        // today
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // select
        val dialog = DatePickerDialog(
            this,
            R.style.DialogStyle,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectDay ->
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = 0
                }
                calendar.set(selectedYear, selectedMonth, selectDay)

                val selected = calendar.time
                val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK)
                viewModel.searchDate = dateFormat.format(selected)
                Log.e("DATE", viewModel.searchDate)

                mainFragment.setSearchDate(viewModel.searchDate)
            }, year, month, day
        )
        dialog.show()
    }
}