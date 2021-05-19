package com.github.scott.gcm.view.activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
    private val REQUEST_PERMISSIONS = 10003


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

        requestPermission()
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


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            var isAllGranted = true
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false
                    break
                }
            }

            if (isAllGranted) {
                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (isAllPermissionsGranted()) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L, // 갱신주기 (ms)
                        0f, // 갱신거리
                        object : LocationListener {
                            override fun onLocationChanged(location: Location) {
                                Log.e(
                                    "CURRENT LOCATION",
                                    "lat: ${location.latitude}     lng: ${location.longitude}"
                                )

                                // 한번만 가져오기위해 리스너 삭제
                                locationManager.removeUpdates(this)

                                viewModel.currentLat = location.latitude
                                viewModel.currentLng = location.longitude

                                mainFragment.setRecommend()
                            }
                        })
                }

            }

        } else {
            val message = "위치 권한을 허용해야 주변 추천 기능을 사용할 수 있습니다."
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }
    }


    private fun requestPermission() {
        val permissions = arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
        requestPermissions(permissions, REQUEST_PERMISSIONS)
    }

    private fun isAllPermissionsGranted(): Boolean {
        var isAllGranted = true
        val permissions = listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        for (permission in permissions) {
            val check = ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            if (!check) {
                isAllGranted = false
                break
            }
        }

        return isAllGranted
    }
}