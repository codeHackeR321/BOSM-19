package com.dvm.appd.bosm.dbg.elas.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.elas.view.adapter.ElasQuestionsAdapter
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasViewModel
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasViewModelFactory
import kotlinx.android.synthetic.main.fra_elas_fragment.*

class ELASFragment : Fragment() {
    private val TAG = "ELAS Fragment"

    private val elasViewModel by lazy {
        ViewModelProviders.of(this, ElasViewModelFactory())[ElasViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fra_elas_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
        elasViewModel.questions.observe(this, Observer {
            Log.d(TAG, "Entered Observer with ${it.toString()}")
            (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).questionsList = it
            (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).notifyDataSetChanged()
        })
    }

    private fun initializeView() {
        var recycler = view!!.findViewById<RecyclerView>(R.id.recycler_elasFrag_questions)
        recycler.adapter = ElasQuestionsAdapter()
    }
}
