package com.dvm.appd.bosm.dbg.elas.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import kotlinx.android.synthetic.main.card_recycler_elas_frag_questions.view.*

class ElasQuestionsAdapter(val listener: onQuestionButtonClicked) : RecyclerView.Adapter<ElasQuestionsAdapter.ElasQuestionsViewHolder>() {

    interface onQuestionButtonClicked {
        fun answerQuestion(questionId: Long)
        fun viewLeaderboard(questionId: Long)
    }

    var questionsList: Map<Long, List<CombinedQuestionOptionDataClass>> = emptyMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElasQuestionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_recycler_elas_frag_questions, parent, false)
        return ElasQuestionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questionsList.size
    }

    override fun onBindViewHolder(holder: ElasQuestionsViewHolder, position: Int) {
        holder.textCategory.text = questionsList.toList()[position].second.first().category
        holder.textQuestion.text = questionsList.toList()[position].second.first().question
        if(questionsList.toList()[position].second.first().isAnswered) {
            holder.buttonLeaderBoard.text = "View Leaderboard"
        } else {
            holder.buttonLeaderBoard.text = "Answer The Question"
        }
        holder.buttonLeaderBoard.setOnClickListener {
            if (holder.buttonLeaderBoard.text == "View Leaderboard") {
                listener.viewLeaderboard(questionsList.toList()[position].first)
            } else {
                listener.answerQuestion(questionsList.toList()[position].first)
            }
        }
    }

    inner class ElasQuestionsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textCategory = view.text_card_elasFrag_category
        val textQuestion = view.text_card_elasFrag_question
        val buttonLeaderBoard = view.bttn_card_elasFrag_leaderboard
    }
}