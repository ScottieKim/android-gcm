package com.github.scott.gcm

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.scott.gcm.data.DBUtil
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.item_community.view.*

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = CommonUtil.getUser(view.context as Activity)
        val dbUtil = DBUtil()
        val user = dbUtil.getUserById(email)
        if (user != null) {
            view.textview_profile_email.text = user.email
            view.textview_profile_name.text = user.name
        }

        Glide.with(view.context as Activity).load(R.drawable.profile).circleCrop().into(view.imageview_profile)

    }
}