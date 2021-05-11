package com.github.scott.gcm.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.scott.gcm.R
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.viewmodel.DetailViewModel
import com.github.scott.gcm.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val title = intent.getStringExtra("title") ?: ""
        if (title.isNullOrBlank()) {
            Toast.makeText(this, "잘못된 정보입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
        }


        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.title = title

        binding.viewModel = viewModel

    }
}