package com.dvm.appd.bosm.dbg.elas.model.repo

import android.annotation.SuppressLint
import android.util.Log
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.retrofit.ElasService
import com.dvm.appd.bosm.dbg.elas.model.room.ElasDao
import com.dvm.appd.bosm.dbg.elas.model.room.OptionData
import com.dvm.appd.bosm.dbg.elas.model.room.QuestionData
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class ElasRepository(val elasDao: ElasDao, val elasService: ElasService) {
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

    private fun parseOption(questionId: Long, option: String): OptionData {
        val optionId = option.substring(option.lastIndexOf('~') + 1).toLong()
        Log.d("EventsRepo", "Extracted OptionId = ${optionId}")
        val optionString = option.subSequence(0, option.lastIndexOf('~')).toString()
        Log.d("EventsRepo", "Extracted Option = ${optionString}")
        return OptionData(option_id = optionId, option = optionString, questionId = questionId)
    }

    @SuppressLint("CheckResult")
    fun setFirebaseListenerForActiveQuestion() {
        FirebaseFirestore.getInstance().collection("quiz").document("active_question").addSnapshotListener { document, error ->
            if (error != null) {
                Log.e(TAG, "Error initializing listener $error")
                setFirebaseListenerForActiveQuestion()
                return@addSnapshotListener
            }
            if (document!!.contains("quiz_closed")) {
                if (document!!["quiz_closed"].toString().toBoolean()) {
                    Log.e(TAG, "The quiz is closed")
                } else {
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
                    val numberOfOptions = try {
                        document["total_options"].toString().toInt()
                    } catch (e: Exception) {
                        4
                    }
                    var optionsList: MutableList<OptionData> = arrayListOf()
                    for(i in 1 .. numberOfOptions) {
                        val option = try {
                            document!![i.toString()].toString()
                        } catch (e: Exception) {
                            "None of These"
                        }
                        optionsList.add(parseOption(question_number, option))
                    }
                    elasDao.insertQuestions(listOf(QuestionData(question_number, question, category))).subscribe({},{
                        Log.e(TAG, "Unable to insert question in room = ${it.toString()}")
                    })
                    elasDao.insertOptions(optionsList).subscribe({},{
                        Log.e(TAG, "Unable to insert options in room = ${it.toString()}")
                    })
                }
            }
        }
    }

    /*fun getQuestionsFromRoom(category: String = "All"): Flowable<List<CombinedQuestionOptionDataClass>> {
        return if (category.equals("All")) {
            elasDao.getAllQuestions().subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        } else {
            elasDao.selectQuestionsInCategory(category).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        }
    }*/
}