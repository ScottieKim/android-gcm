package com.github.scott.gcm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.R
import com.github.scott.gcm.data.viewmodel.MainViewModel
import com.github.scott.gcm.databinding.FragmentMycommunityBinding
import com.github.scott.gcm.view.activity.MainActivity

class MyCommunityFragment : Fragment() {
    private lateinit var binding: FragmentMycommunityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mycommunity, container, false)
        //val view = inflater.inflate(R.layout.fragment_mycommunity, container, false)
        viewModel = ViewModelProvider(context as MainActivity).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}