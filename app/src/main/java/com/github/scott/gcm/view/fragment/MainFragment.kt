package com.github.scott.gcm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.R
import com.github.scott.gcm.data.viewmodel.MainViewModel
import com.github.scott.gcm.databinding.FragmentMainBinding
import com.github.scott.gcm.view.activity.MainActivity
import com.github.scott.gcm.view.adapter.CommunityListAdapter

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private var isFirst = true

    // 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        return binding.root
    }

    // 2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val list = viewModel.getAllCommunity()
        binding.recyclerviewMainNew.adapter = CommunityListAdapter(list)
        binding.recyclerviewMainRecom.adapter = CommunityListAdapter(list)
        binding.viewModel = viewModel

        val sList = listOf("Basketball", "Golf", "Soccer")
        binding.spinnerCreateCommunitytype.adapter =
            ArrayAdapter(binding.root.context, R.layout.item_spinner, R.id.textview_spinner, sList)

        binding.spinnerCreateCommunitytype.addOnLayoutChangeListener(View.OnLayoutChangeListener { view, i, i1, i2, i3, i4, i5, i6, i7 ->
            val position: Int = binding.spinnerCreateCommunitytype.selectedItemPosition
            if (isFirst) {
                isFirst = false
            } else {
                // viewModel.setCommunityType(list[position])
                viewModel.searchType = sList[position]
                binding.textviewCreateCommunitytype.text = sList[position]
            }
        })

    }

    fun refreshList() {
        val list = viewModel.getAllCommunity()
        (binding.recyclerviewMainNew.adapter as CommunityListAdapter).setNewList(list)
    }

    fun setSearchDate(date: String) {
        binding.textviewMainSearchDate.text = date
    }

}