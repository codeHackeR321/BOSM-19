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
        Log.d(TAG, "Init for Repo Called")
        initializeFireStore()
    }

    private fun initializeFireStore() {
        FirebaseFirestore.getInstance().collection("elas").addSnapshotListener { snapshot, error ->
            Log.d(TAG, "Entered firestore Listener with ${snapshot!!.documents.size} doucuments")
            var questionList = emptyList<QuestionData>()
            var optionList = emptyList<OptionData>()
            if (error != null) {
                Log.e(TAG, "Error initializing listener $error")
                initializeFireStore()
                return@addSnapshotListener
            }
            for (document in snapshot!!.documents) {
                Log.d(TAG, "Recived document = ${document.id}")
                val questionId = document["id"].toString().toLong()
                elasDao.deleteOptionsForQuestionWithId(questionId).subscribeOn(Schedulers.io()).doOnError { Log.e(TAG, "Error in delteing databsae = $it") }.subscribe()
                questionList = questionList.plus(
                    QuestionData(
                        correctAnswerId = document["answer"].toString().toInt(),
                        category = document["category"].toString(),
                        questionId = questionId,
                        timestamp = document["timestamp"].toString(),
                        isAnswered = false,
                        question = document["question"].toString()
                    )
                )
                val options = (document["options"] as ArrayList<String>)
                for ((index, option) in options.iterator().withIndex()) {
                    optionList = optionList.plus(
                        OptionData(
                            id = 0,
                            questionId = questionId,
                            answerId = index,
                            option = option
                        )
                    )
                }
            }
            elasDao.insertQuestions(questionList).subscribeOn(Schedulers.io()).doOnError { Log.e(TAG, "Error in adding questions databsae = $it") }.subscribe()
            elasDao.insertOptions(optionList).subscribeOn(Schedulers.io()).doOnError { Log.e(TAG, "Error in adding options databsae = $it") }.subscribe()
        }
    }

    fun getQuestionsFromRoom(category: String = "All"): Flowable<List<CombinedQuestionOptionDataClass>> {
        return if (category.equals("All")) {
            elasDao.getAllQuestions().subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        } else {
            elasDao.selectQuestionsInCategory(category).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        }
    }
}