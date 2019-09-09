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
        fun viewRules(questionId: String)
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
        holder.textQuestionNumber.text = "Question ${questionsList.toList()[position].second.first().category}"
        holder.textQuestion.text = questionsList.toList()[position].second.first().question
        holder.buttonRules.setOnClickListener {
            listener.viewRules(questionsList.toList()[position].second.first().category)
        }
    }

    inner class ElasQuestionsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textQuestionNumber = view.text_card_elasFrag_questionNo
        val textQuestion = view.text_card_elasFrag_question
        val buttonRules = view.text_card_elasFrag_Rules
    }
}