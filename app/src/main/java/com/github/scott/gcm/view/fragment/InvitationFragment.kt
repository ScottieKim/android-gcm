package com.github.scott.gcm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.FragmentInviteBinding
import com.github.scott.gcm.view.adapter.UserListAdapter

class InvitationFragment : Fragment() {
    private lateinit var binding: FragmentInviteBinding
    private lateinit var viewModel: AlertViewModel
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite, container, false)
        viewModel = ViewModelProvider(activity as AlertActivity).get(AlertViewModel::class.java)
        viewModel.setUserList.observe(activity as AlertActivity, Observer {
            adapter.setUserList(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserListAdapter(mutableListOf(), viewModel, true)
        binding.recyclerviewInviewList.adapter = adapter
        binding.viewModel = viewModel

    }

}