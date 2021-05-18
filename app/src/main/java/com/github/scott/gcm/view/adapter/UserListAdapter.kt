package com.github.scott.gcm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.ItemSearchuserBinding

class UserListAdapter(var list: MutableList<User>, val viewModel: AlertViewModel, val isSearch : Boolean) :
    RecyclerView.Adapter<UserListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemSearchuserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_searchuser,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    fun setUserList(list: MutableList<User>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        this.list.removeAt(position)
        this.notifyDataSetChanged()
    }

    inner class Holder(val binding: ItemSearchuserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.isSearch = isSearch
            binding.user = user
            binding.viewModel = viewModel
            binding.position = adapterPosition
        }
    }
}