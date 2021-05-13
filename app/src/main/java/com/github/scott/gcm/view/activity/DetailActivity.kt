package com.github.scott.gcm.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.R
import com.github.scott.gcm.data.viewmodel.DetailViewModel
import com.github.scott.gcm.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
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
        viewModel.showToast.observe(this, Observer { })
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
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