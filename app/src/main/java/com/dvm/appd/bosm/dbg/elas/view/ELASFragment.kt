package com.dvm.appd.bosm.dbg.elas.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.elas.model.UIStateElas
import com.dvm.appd.bosm.dbg.elas.view.adapter.ElasQuestionsAdapter
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasViewModel
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_elas_fragment.*
import kotlinx.coroutines.selects.select

class ELASFragment : Fragment(), ElasQuestionsAdapter.onQuestionButtonClicked {

    private val TAG = "ELAS Fragment"

    private val elasViewModel by lazy {
        ViewModelProviders.of(this, ElasViewModelFactory())[ElasViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.search.isVisible = false
        activity!!.textView7.isVisible = false
        selectQuestions()
        return inflater.inflate(R.layout.fra_elas_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()

        activity!!.bttn_Questions_elas.setOnClickListener {
            Log.d("ElasFragment", "Questions Selected")
            selectQuestions()
        }

        activity!!.bttn_Leaderboard_elas.setOnClickListener {
            Log.d("ElasFragment", "Options Selected")
            selectLeaderboard()
        }

        elasViewModel.uiState.observe(this, Observer {
            when(it!!) {
                UIStateElas.Loading -> {
                    progress_fra_elas.visibility = View.VISIBLE
                    activity!!.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
                is UIStateElas.Failure -> {
                    progress_fra_elas.visibility = View.INVISIBLE
                    activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Snackbar.make(activity!!.coordinator_parent, (it as UIStateElas.Failure).message, Snackbar.LENGTH_INDEFINITE).show()
                }
                is UIStateElas.Questions -> {
                    progress_fra_elas.visibility = View.INVISIBLE
                    activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).questionsList = (it as UIStateElas.Questions).questions
                    (recycler_elasFrag_questions.adapter as ElasQuestionsAdapter).notifyDataSetChanged()
                }
            }
        })
    }

    private fun initializeView() {
        val recycler = view!!.findViewById<RecyclerView>(R.id.recycler_elasFrag_questions)
        recycler.adapter = ElasQuestionsAdapter(this)

        activity!!.cart.setOnClickListener{
            this.findNavController().navigate(R.id.action_action_game_to_action_cart)
        }

        activity!!.profile.setOnClickListener{
            this.findNavController().navigate(R.id.action_action_game_to_action_profile)
        }

        activity!!.notifications.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_game_to_notificationFragment)
        }
    }

    private fun selectQuestions() {
        activity!!.bttn_Questions_elas.setTextColor(Color.BLACK)
        activity!!.bttn_Leaderboard_elas.setTextColor(resources.getColor(R.color.colorGrey))
    }

    private fun selectLeaderboard() {
        activity!!.bttn_Leaderboard_elas.setTextColor(Color.BLACK)
        activity!!.bttn_Questions_elas.setTextColor(resources.getColor(R.color.colorGrey))
    }

    override fun onResume() {
        (activity!! as MainActivity).showCustomToolbar()
        activity!!.linearElasRecycler.isVisible = true
        activity!!.fragmentName.text = "Quiz"
        super.onResume()
    }

    override fun onPause() {
        activity!!.linearElasRecycler.isVisible = false
        super.onPause()
    }

    override fun answerQuestion(questionId: Long) {
        val bundle = bundleOf("questionId" to questionId)
        view!!.findNavController().navigate(R.id.action_action_game_to_ELASQuestionFragment, bundle)
    }

    override fun viewRules(questionId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
