package com.github.scott.gcm.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.Community
import kotlinx.android.synthetic.main.item_community.view.*

class CommunityListAdapter(var list: List<Community>) :
    RecyclerView.Adapter<CommunityListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Community) {
            itemView.textview_community_title.text = item.title
            itemView.textview_community_desc.text = item.description

            Glide.with(itemView.context).load(item.img).into(itemView.imageview_community_profile)
        }
    }
}