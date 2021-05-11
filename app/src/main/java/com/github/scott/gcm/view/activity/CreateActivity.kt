package com.github.scott.gcm.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.viewmodel.CreateViewModel
import com.github.scott.gcm.databinding.ActivityCreateBinding
import com.github.scott.gcm.view.adapter.CommunityListAdapter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var viewModel: CreateViewModel
    private val PICK_IMAGE = 10001
    private val PICK_LOCATION = 10002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create)
        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        viewModel.close.observe(this, Observer { finish() })
        viewModel.moveGallery.observe(this, Observer {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
            startActivityForResult(intent, PICK_IMAGE)
        })
        viewModel.moveMap.observe(this, Observer {
            val intent = Intent(this, MapActivity::class.java)
            startActivityForResult(intent, PICK_LOCATION)
        })
        viewModel.showToast.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.setCommunityOwner(CommonUtil.getUser(this))
        binding.viewModel = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode != Activity.RESULT_CANCELED) {
            val uri = data?.data
            upload(uri!!)
        } else if (requestCode == PICK_LOCATION && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra("title")
            val address = data?.getStringExtra("address")
            val lat = data?.getDoubleExtra("lat", 0.0)
            val lng = data?.getDoubleExtra("lng", 0.0)

            viewModel.setCommunityLatLng(lat!!, lng!!)
            binding.textviewCreateCommunitylocationcontent.text = address
        }
    }

    private fun upload(uri: Uri) {
        val storage = Firebase.storage
        val storageRef = storage.reference

        val sdf = SimpleDateFormat("yyyyMMddhhmmss")
        val fileName = sdf.format(Date()) + ".jpg"
        val riversRef = storageRef.child("/$fileName")

        riversRef.putFile(uri)
            .addOnFailureListener {
                Toast.makeText(this, "업로드가 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener {
                Toast.makeText(this, "업로드가 완료되었습니다", Toast.LENGTH_SHORT).show()
                loadImg(fileName)
            }

    }

    private fun loadImg(img: String) {
        Firebase.storage.reference.child(img).downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(binding.imageviewCreatePhoto)
            viewModel.setCommunityImg(it.toString())
            Log.e("IMG", it.toString())
        }
    }
}