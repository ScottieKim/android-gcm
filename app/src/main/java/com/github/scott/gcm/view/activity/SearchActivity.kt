package com.github.scott.gcm.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.viewmodel.SearchViewModel
import com.github.scott.gcm.databinding.ActivitySearchBinding
import com.github.scott.gcm.view.adapter.CommunityListAdapter


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.date = intent.getStringExtra("date") ?: ""
        viewModel.type = intent.getStringExtra("type") ?: ""
        binding.viewModel = viewModel
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:communityList")
        fun setCommunity(view: RecyclerView, list: MutableList<Community>) {
            view.adapter = CommunityListAdapter(list)
        }
    }
}