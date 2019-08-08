package com.dvm.appd.bosm.dbg.more

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dvm.appd.bosm.dbg.R

class MoreFragment : Fragment() {

    var moreItems = listOf("Contact Us", "Developers", "Map", "EPC Blog", "HPC Blog", "Picture Gallery")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
