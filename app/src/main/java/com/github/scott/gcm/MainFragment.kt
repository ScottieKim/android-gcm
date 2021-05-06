package com.github.scott.gcm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.scott.gcm.data.Community
import com.github.scott.gcm.data.DBUtil
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    // 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    // 2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val dbUtil = DBUtil()

        // Insert Test
        dbUtil.insertCommunity(Community().apply {
            title = "comm3"
            description = "desc3"
            img ="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"
        })
        dbUtil.insertCommunity(Community().apply {
            title = "comm4"
            description = "desc2"
            img ="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"

        })
        dbUtil.insertCommunity(Community().apply {
            title = "comm5"
            description = "desc3"
            img ="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMTFfNzYg%2FMDAxNjA1MDU4MjUzMDEw.orHYaSZZJ-L8P2i-v-0RNqz1N_DBWxx_nCy6tTgw-Isg.TaGs_IMX4KEuUBlCzUfNSSz7wB-SSZzAwj5DzwK763sg.JPEG.cldpidfoh20%2FKakaoTalk_20201109_120808460_06.jpg&type=a340"

        })

        val list = dbUtil.getAllCommunity()
        view.recyclerview_main_new.adapter = CommunityListAdapter(list)
        view.recyclerview_main_recom.adapter = CommunityListAdapter(list)
    }

}