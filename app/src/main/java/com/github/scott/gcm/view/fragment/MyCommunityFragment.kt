package com.github.scott.gcm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.scott.gcm.R
import com.github.scott.gcm.databinding.FragmentMycommunityBinding

class MyCommunityFragment : Fragment() {
    private lateinit var binding :FragmentMycommunityBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mycommunity, container, false)
        //val view = inflater.inflate(R.layout.fragment_mycommunity, container, false)
        return binding.root
    }
}