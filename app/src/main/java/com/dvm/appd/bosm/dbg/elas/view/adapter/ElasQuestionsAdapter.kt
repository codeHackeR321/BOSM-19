package com.dvm.appd.bosm.dbg.elas.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.elas.model.CombinedQuestionOptionDataClass
import kotlinx.android.synthetic.main.card_recycler_elas_frag_questions.view.*

class ElasQuestionsAdapter : RecyclerView.Adapter<ElasQuestionsAdapter.ElasQuestionsViewHolder>() {

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
    }

    inner class ElasQuestionsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textCategory = view.text_card_elasFrag_category
        val textQuestion = view.text_card_elasFrag_question
        val buttonLeaderBoard = view.bttn_card_elasFrag_leaderboard
    }
}