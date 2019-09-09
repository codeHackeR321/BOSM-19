package com.dvm.appd.bosm.dbg.elas.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.dvm.appd.bosm.dbg.MainActivity

import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.activity_main.*

class ELASQuestionFragment : Fragment() {

    var questionId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity!! as MainActivity).hideCustomToolbarForLevel2Fragments()
        activity!!.search.isVisible = false
        activity!!.textView7.isVisible = false
        // Inflate the layout for this fragment
        questionId = arguments?.getLong("questionId")!!
        return inflater.inflate(R.layout.fragment_elasquestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
