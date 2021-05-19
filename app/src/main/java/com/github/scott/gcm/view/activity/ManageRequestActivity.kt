package com.github.scott.gcm.view.activity

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.model.User
import com.github.scott.gcm.data.viewmodel.AlertViewModel
import com.github.scott.gcm.databinding.ActivityRequestmanageBinding
import com.github.scott.gcm.view.adapter.UserListAdapter

class ManageRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestmanageBinding
    private lateinit var viewModel: AlertViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun showRequestDialog(position: Int, isAccepted: Boolean) {
            CommonUtil.showDialog(context = this,
                message = if (isAccepted) resources.getString(R.string.managerequest_dialog_message_accept)
                else resources.getString(R.string.managerequest_dialog_message_deny),
                positiveTask = {
                    val adapter = (binding.recyclerviewManage.adapter as UserListAdapter)
                    val item = adapter.list[position]
                    viewModel.manageRequest(item, isAccepted, true )
                    adapter.deleteItem(position)

                    val msg =
                        if (isAccepted) resources.getString(R.string.managerequest_result_message_accept)
                        else resources.getString(R.string.managerequest_result_message_deny)
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                },
                negativeTask = {})
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_requestmanage)
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)
        viewModel.clickAccept.observe(this, Observer { position ->
            showRequestDialog(position, true)
        })

        viewModel.clickDeny.observe(this, Observer { position ->
            showRequestDialog(position, false)
        })
        binding.viewModel = viewModel

    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:joinList")
        fun setRequestList(view: RecyclerView, list: MutableList<User>) {
            if (view.adapter == null) {
                val viewModel = ViewModelProvider(
                    view.context as ManageRequestActivity,
                    ViewModelProvider.AndroidViewModelFactory((view.context as Activity).application)
                ).get(AlertViewModel::class.java)

                view.adapter = UserListAdapter(list, viewModel, false)
            } else {
                (view.adapter as UserListAdapter).setUserList(list)
            }
        }
    }

}