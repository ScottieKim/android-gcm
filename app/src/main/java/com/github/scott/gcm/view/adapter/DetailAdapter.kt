package com.github.scott.gcm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.github.scott.gcm.data.model.Community
import kotlinx.android.synthetic.main.activity_detail.view.*


class DetailAdapter(val context: Context, val Community: ArrayList<Community>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_detail, null)

        val img = view.findViewById<ImageView>(R.id.imageview_detail_photo)

    }

    override fun getItem(position: Int): Any {
    }

    override fun getItemId(position: Int): Long {
    }

    override fun getCount(): Int {
    }
}
