package com.github.scott.gcm.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.view.adapter.CommunityListAdapter
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.viewmodel.MainViewModel
import com.github.scott.gcm.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    // 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    // 2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {

        viewModel.insertTestCommunity()

        val list = viewModel.getAllCommunity()
        view.recyclerview_main_new.adapter = CommunityListAdapter(list)
        view.recyclerview_main_recom.adapter = CommunityListAdapter(list)


    }

}