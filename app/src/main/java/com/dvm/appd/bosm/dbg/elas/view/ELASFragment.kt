package com.dvm.appd.bosm.dbg.elas.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.elas.model.UIStateElas
import com.dvm.appd.bosm.dbg.elas.view.adapter.ElasQuestionsAdapter
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasViewModel
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fra_elas_fragment.*

class ELASFragment : Fragment(), ElasQuestionsAdapter.onQuestionButtonClicked {

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
        elasViewModel.uiState.observe(this, Observer {
            when(it!!) {
                UIStateElas.Loading -> {
                    progress_fra_elas.visibility = View.VISIBLE
                    activity!!.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
                is UIStateElas.Failure -> {
                    progress_fra_elas.visibility = View.INVISIBLE
                    activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Snackbar.make(view, (it as UIStateElas.Failure).message, Snackbar.LENGTH_INDEFINITE).show()
                }
                is UIStateElas.Questions -> {
                    progress_fra_elas.visibility = View.INVISIBLE
                    activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).questionsList = (it as UIStateElas.Questions).questions
                    (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).notifyDataSetChanged()
                }
            }
        })
        /*elasViewModel.questions.observe(this, Observer {
            Log.d(TAG, "Entered Observer with ${it.toString()}")
            (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).questionsList = it
            (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).notifyDataSetChanged()
        })*/
    }

    private fun initializeView() {
        val recycler = view!!.findViewById<RecyclerView>(R.id.recycler_elasFrag_questions)
        recycler.adapter = ElasQuestionsAdapter(this)
    }

    override fun answerQuestion(questionId: Long) {
        val bundle = bundleOf("questionId" to questionId)
        view!!.findNavController().navigate(R.id.action_action_game_to_ELASQuestionFragment, bundle)
    }

    override fun viewLeaderboard(questionId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
