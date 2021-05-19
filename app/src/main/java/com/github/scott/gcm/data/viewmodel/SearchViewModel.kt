package com.github.scott.gcm.data.viewmodel

import androidx.lifecycle.ViewModel
import com.github.scott.gcm.data.DBUtil
import com.github.scott.gcm.data.model.Community
import java.text.DateFormat
import java.util.*

class SearchViewModel : ViewModel() {
    private val dbUtil = DBUtil()
    var type = ""
    var date = ""
    var isEmpty = true
    var clickBack = MainViewModel.CalledData()

    fun getCommunityByType(): MutableList<Community> {
        val result = mutableListOf<Community>()
        val list = dbUtil.getCommunityByType(type).toMutableList()

        for (item in list) {
            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK)
            val startDate = dateFormat.parse(item.startDate)
            val endDate = dateFormat.parse(item.endDate)
            val compareDate = dateFormat.parse(date)

            if (startDate != null && endDate != null && compareDate != null) {
                if (startDate < compareDate && compareDate < endDate) {
                    result.add(item)
                }
            }
        }

        isEmpty = result.isEmpty()

        return result
    }

    fun onClickBack() {
        clickBack.call()
    }

}