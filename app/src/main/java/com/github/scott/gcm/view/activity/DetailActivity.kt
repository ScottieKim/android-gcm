package com.github.scott.gcm.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.viewmodel.DetailViewModel
import com.github.scott.gcm.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title") ?: ""
        if (title.isNullOrBlank()) {
            Toast.makeText(this, "잘못된 정보입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
        }

        initViewModel(title)
        initBinding()
        initMap()
    }

    private fun initViewModel(title: String) {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.title = title
        viewModel.showToast.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.clickJoin.observe(this, Observer {
            CommonUtil.showDialog(this, "참가 신청을 하시겠습니까? ", { viewModel.requestJoin() }, {})
        })
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
        binding.ratingbarDetailReview.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure to rate?")
                .setPositiveButton("Yes") { dialog, id ->
                    Toast.makeText(this, "평점 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    binding.ratingbarDetailReview.setIsIndicator(false)

                    val title = intent.getStringExtra("title") ?: ""
                    val email = CommonUtil.getUser(this)
                    viewModel.insertReview(title, email, rating)

                }
                .setNegativeButton("No") { dialog, id ->
                }
            // Create the AlertDialog object and return it
            builder.create()
            builder.show()
        }
    }

    private fun initMap() {
        // 지도 초기화
        val supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map_detail_location) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap ->
            if (viewModel.community != null) {
                val latLng = LatLng(viewModel.community?.lat!!, viewModel.community?.lng!!)
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                googleMap.addMarker(markerOptions)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
    }
}