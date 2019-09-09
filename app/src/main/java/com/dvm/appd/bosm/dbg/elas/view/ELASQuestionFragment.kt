package com.dvm.appd.bosm.dbg.elas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.MainActivity

import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.elas.model.UIStateElas
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasQuestionViewModel
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasQuestionViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_elas_fragment.*

class ELASQuestionFragment : Fragment() {

    var questionId: Long = 0
    val elasQuestionViewModel: ElasQuestionViewModel by lazy {
        ViewModelProviders.of(this, ElasQuestionViewModelFactory())[ElasQuestionViewModel::class.java]
    }

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
        if (questionId != 0.toLong()) {
            elasQuestionViewModel.getQuestion(questionId)
        }

        elasQuestionViewModel.uiState.observe(this, Observer {
            when(it) {
                is UIStateElas.Failure -> {
                    progress_fra_elas.visibility = View.INVISIBLE
                    activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Snackbar.make(activity!!.coordinator_parent, (it as UIStateElas.Failure).message, Snackbar.LENGTH_INDEFINITE).show()
                }
                is UIStateElas.Loading -> {
                    progress_fra_elas.visibility = View.VISIBLE
                    activity!!.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
                is UIStateElas.Questions -> {
                    progress_fra_elas.visibility = View.INVISIBLE
                    activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            }
        })

        elasQuestionViewModel.question.observe(this, Observer {

        })
    }
}
