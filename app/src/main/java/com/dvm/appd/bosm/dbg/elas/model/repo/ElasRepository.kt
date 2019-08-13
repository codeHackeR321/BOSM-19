package com.dvm.appd.bosm.dbg.elas.model.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.elas.model.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.room.ElasDao
import com.dvm.appd.bosm.dbg.elas.model.room.OptionData
import com.dvm.appd.bosm.dbg.elas.model.room.QuestionData
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.sql.Timestamp

class ElasRepository(val elasDao: ElasDao) {
    private val TAG = "ELAS REPO"

    init {
        initializeFireStore()
    }

    private fun initializeFireStore() {
        FirebaseFirestore.getInstance().collection("elas").addSnapshotListener{ snapshot, error ->
            var questionList = emptyList<QuestionData>()
            var optionList = emptyList<OptionData>()
            if (error != null){
                Log.e(TAG, "Error initializing listener $error")
                initializeFireStore()
                return@addSnapshotListener
            }
            for(document in snapshot!!.documents){
                Log.d(TAG, "Recived document = ${document.id}")
                val questionId = document["id"].toString().toLong()
                elasDao.deleteOptionsForQuestionWithId(questionId)
                questionList = questionList.plus(
                    QuestionData(
                        correctAnswerId = document["answer"].toString().toInt(),
                        category = document["category"].toString(),
                        questionId = questionId,
                        timestamp = document["timestamp"] as Timestamp,
                        isAnswered = false,
                        question = document["question"].toString()
                ))
                val options = (document["options"] as Array<String>)
                for((index,option) in options.iterator().withIndex()){
                    optionList = optionList.plus(
                        OptionData(
                            id = 0,
                            questionId = questionId,
                            answerId = index,
                            option = option
                    ))
                }
            }
            elasDao.insertQuestions(questionList)
            elasDao.insertOptions(optionList)
        }
    }

    fun getQuestionsFromRoom(category: String = "All"): Flowable<List<CombinedQuestionOptionDataClass>> {
        return if (category.equals("All")) {
            elasDao.getAllQuestions().subscribeOn(Schedulers.io())
        }else {
            elasDao.selectQuestionsInCategory(category).subscribeOn(Schedulers.io())
        }
    }
}