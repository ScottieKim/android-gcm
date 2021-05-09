package com.github.scott.gcm.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.view.adapter.CommunityListAdapter
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.viewmodel.MainViewModel
import com.github.scott.gcm.databinding.FragmentMainBinding
import com.github.scott.gcm.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    // 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        return binding.root
    }

    // 2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val list = viewModel.getAllCommunity()
        binding.recyclerviewMainNew.adapter = CommunityListAdapter(list)
        binding.recyclerviewMainRecom.adapter = CommunityListAdapter(list)
    }

    fun refreshList() {
        val list = viewModel.getAllCommunity()
        (binding.recyclerviewMainNew.adapter as CommunityListAdapter).setNewList(list)
    }

}