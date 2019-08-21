package com.dvm.appd.bosm.dbg.more

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.fragment_fragment_recycler_view.*

class FragmentRecyclerView : Fragment() {

    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString("title")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        when(title) {
            "Contact Us" -> {
                recycler_commonRecyclerView.adapter = ContactUsAdapter()
                (recycler_commonRecyclerView.adapter as ContactUsAdapter).notifyDataSetChanged()
            }
        }
    }

}
