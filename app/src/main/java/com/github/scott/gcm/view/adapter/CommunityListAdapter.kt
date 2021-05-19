package com.github.scott.gcm.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.databinding.ItemCommunityBinding
import com.github.scott.gcm.view.activity.DetailActivity
import com.github.scott.gcm.view.activity.MainActivity
import com.github.scott.gcm.view.activity.SearchActivity
import kotlinx.android.synthetic.main.item_community.view.*

class CommunityListAdapter(var list: List<Community>) :
    RecyclerView.Adapter<CommunityListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemCommunityBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_community,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    inner class Holder(var binding: ItemCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Community) {
            binding.community = item
            binding.constraintlayoutCommunityContainer.setOnClickListener {
                var activity: Activity? = null
                var intent: Intent? = null

                when (binding.root.context) {
                    is MainActivity -> {
                        activity = binding.root.context as MainActivity
                    }
                    is SearchActivity -> {
                        activity = binding.root.context as SearchActivity
                    }
                }

                intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("title", item.title)
                activity?.startActivity(intent)
            }
        }
    }

    fun setNewList(list: List<Community>) {
        this.list = list
        notifyDataSetChanged()
    }


    companion object {
        @JvmStatic
        @BindingAdapter("app:imgUrl")
        fun setImageUrl(view: ImageView, url: String) {
            if (url.isNotBlank()) {
                Glide.with(view.context).load(url).into(view)
            }
        }
    }
}