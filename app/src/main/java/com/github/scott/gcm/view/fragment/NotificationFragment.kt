package com.github.scott.gcm.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.Invitation
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.FragmentNotificationBinding
import com.github.scott.gcm.view.activity.AlertActivity
import com.github.scott.gcm.view.activity.ManageRequestActivity
import com.github.scott.gcm.view.adapter.NotificationListAdapter
import com.github.scott.gcm.view.adapter.UserListAdapter

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var viewModel: AlertViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as AlertActivity).get(AlertViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    fun deleteListItem(position: Int) {
        (binding.recyclerviewNotification.adapter as NotificationListAdapter).deleteItem(position)
    }

    fun getListItem(position: Int): Invitation {
        val invitation =
            (binding.recyclerviewNotification.adapter as NotificationListAdapter).list[position]
        return invitation
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:InvitationList")
        fun setInvitationList(view: RecyclerView, list: MutableList<Invitation>) {
            if (view.adapter == null) {
                val viewModel = ViewModelProvider(
                    view.context as AlertActivity,
                    ViewModelProvider.AndroidViewModelFactory((view.context as Activity).application)
                ).get(AlertViewModel::class.java)

                view.adapter = NotificationListAdapter(list, viewModel)
            } else {
                (view.adapter as NotificationListAdapter).setUserList(list)
            }
        }
    }

}