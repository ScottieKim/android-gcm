package com.github.scott.gcm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.Invitation
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.ItemNotificationBinding

class NotificationListAdapter(var list: MutableList<Invitation>, var viewModel: AlertViewModel) :
    RecyclerView.Adapter<NotificationListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemNotificationBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_notification,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    fun setUserList(list: MutableList<Invitation>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        this.list.removeAt(position)
        this.notifyDataSetChanged()
    }

    inner class Holder(var binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Invitation) {
            binding.invitation = item
            binding.viewModel = viewModel.apply { notificationList = list }
            binding.position = absoluteAdapterPosition
        }
    }
}