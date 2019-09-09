package com.dvm.appd.bosm.dbg.elas.model.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.room.ElasDao
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class ElasRepository(val elasDao: ElasDao) {
    private val TAG = "ELAS REPO"

    init {
        Log.d(TAG, "Init for Repo Called")
        // initializeFireStore()
    }

    /*private fun initializeFireStore() {
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
                        category = document["category"].toString(),
                        questionId = questionId,
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
    }*/

    /*fun ElasViewModel.lsitenForActiveQuestion() {
        FirebaseFirestore.getInstance().collection("quiz").document("active_question").addSnapshotListener { document, error ->
            if (error != null) {
                this.uiState.asMut().postValue(UIStateElas.Failure("Failed to Connect with the backend Service"))
                return@addSnapshotListener
            }
            val isQuizClozed = try {
                document!!["quiz_closed"]!!.toString().toBoolean()
            } catch (e: Exception) {
                true
            }
            if (isQuizClozed) {
                this.uiState.asMut().postValue(UIStateElas.Failure("The quiz is currently closed"))
            } else {
                var list: MutableList<CombinedQuestionOptionDataClass> = arrayListOf()
                var questionData: QuestionData
                val question_number = try {
                    document!!["question_no"].toString().toLong()
                } catch (e: Exception) {
                    0.toLong()
                }
                val question = try {
                    document!!["question_text"].toString()
                } catch (e: Exception) {
                    "Not Available"
                }
                val category = try {
                    document!!["category"].toString()
                } catch (e: Exception) {
                    "Miscellaneous"
                }
                val option1 = try {
                    document!!["1"].toString()
                } catch (e: Exception) {
                    "None of These"
                }
                val option2 = try {
                    document!!["2"].toString()
                } catch (e: Exception) {
                    "None of These"
                }
                val option3 = try {
                    document!!["3"].toString()
                } catch (e: Exception) {
                    "None of These"
                }
                val option4 = try {
                    document!!["4"].toString()
                } catch (e: Exception) {
                    "None of These"
                }
                questionData = QuestionData(questionId = question_number, question = question, isAnswered = false, category = category)
            }
        }
    }*/

    fun getQuestionsFromRoom(category: String = "All"): Flowable<List<CombinedQuestionOptionDataClass>> {
        return if (category.equals("All")) {
            elasDao.getAllQuestions().subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        } else {
            elasDao.selectQuestionsInCategory(category).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        }
    }
}