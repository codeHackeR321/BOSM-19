package com.dvm.appd.bosm.dbg.elas.model.repo

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.retrofit.*
import com.dvm.appd.bosm.dbg.elas.model.room.ElasDao
import com.dvm.appd.bosm.dbg.elas.model.room.OptionData
import com.dvm.appd.bosm.dbg.elas.model.room.QuestionData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Response

class ElasRepository(val elasDao: ElasDao, val elasService: ElasService, val authRepository: AuthRepository) {
    private val TAG = "ELAS REPO"
    private val jwt = authRepository.getUser().toSingle().flatMap { return@flatMap Single.just("jwt ${it.jwt}") }

    init {
        Log.d(TAG, "Init for Repo Called")
        setFirebaseListenerForActiveQuestion()
        setFirebaseListenerForLeaderboard()
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

    fun setFirebaseListenerForLeaderboard() {
        FirebaseFirestore.getInstance().collection("quiz").document("leaderboard").addSnapshotListener { document, error ->
            var list: MutableList<PlayerRankingResponse> = arrayListOf()
            for (i in 1 .. 20) {
                Log.d("ElasRepo", "Value of i = $i")
                var listItem: PlayerRankingResponse
                try {
                    listItem = parseRanking(i, document!![i.toString()].toString())
                    list.add(listItem)
                } catch (e: Exception) {
                    Log.d("ElasRepo", "Error in reading firestore = ${e.toString()}")
                }
            }
            elasDao.deleteLeaderboardData().subscribeOn(Schedulers.io()).subscribe({
                elasDao.insertLeaderboardPlayer(list).subscribeOn(Schedulers.io()).subscribe({},{
                    Log.e("Elas Repo", "Error inserting player in database = ${it.toString()}")
                })
            },{
                Log.e("Elas Repo", "Error deleting database = ${it.toString()}")
            })
        }
    }

    fun getLeaderboardFromRoom(): Flowable<List<PlayerRankingResponse>> {
        return elasDao.getLeaderboardFromRoon().subscribeOn(Schedulers.io())
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
                    elasDao.insertQuestions(listOf(QuestionData(question_number, question, category))).subscribeOn(Schedulers.io()).subscribe({},{
                        Log.e(TAG, "Unable to insert question in room = ${it.toString()}")
                    })
                    elasDao.insertOptions(optionsList).subscribeOn(Schedulers.io()).subscribe({},{
                        Log.e(TAG, "Unable to insert options in room = ${it.toString()}")
                    })
                }
            }
        }
    }

    fun getParticularQuestionFromRoom(id: Long): Single<List<CombinedQuestionOptionDataClass>> {
        return elasDao.selectParticularQuestionRoom(id).subscribeOn(Schedulers.io())
    }

    fun submitAnswerForQuestion(questionId: Long, optionId: Long): Single<Response<AnswerResponse>> {
        val body = JsonObject().also {
            it.addProperty("question_id", questionId.toInt())
            it.addProperty("answer_id", optionId.toInt())
        }
        Log.d("ELasRepo", "Body Sent = ${body}")
        return elasService.answerQuestion(jwt.blockingGet().toString(), body).subscribeOn(Schedulers.io())
    }

    fun getQuestionsFromRoom(category: String = "All"): Flowable<List<CombinedQuestionOptionDataClass>> {
        return if (category.equals("All")) {
            elasDao.getAllQuestions().subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        } else {
            elasDao.selectQuestionsInCategory(category).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
        }
    }

    private fun parseRanking(rank: Int, ranking: String): PlayerRankingResponse {
        val points = ranking.substring(ranking.lastIndexOf('~') + 1).toInt()
        Log.d("ElasRepo", "Points = $points")
        val name = ranking.subSequence(0, ranking.lastIndexOf('~')).toString()
        Log.d("ElasRepo", "Name = $name")
        return PlayerRankingResponse(name, points, rank)
    }

    fun getRulesForRoundsFromBackend(): Single<Response<RulesResponse>> {
        return elasService.getAllRules().subscribeOn(Schedulers.io())
    }

    fun getQuestions():Completable{

        return authRepository.getUser()
            .flatMapCompletable{
                elasService.getAllQuestions("JWT ${it.jwt}").doOnSuccess {qResponse ->
                    Log.d("checkelasr",qResponse.code().toString())
                    Log.d("checkelasr",qResponse.body().toString())
                    val questions: MutableList<QuestionData> = arrayListOf()
                    val options: MutableList<OptionData> = arrayListOf()
                    val questionsData= try {
                        qResponse.body()!!.previous_questions
                    } catch (e: Exception) {
                        emptyList<Map<String, Any>>()
                    }
                    if (!questionsData.isNullOrEmpty()) {
                        questionsData.forEach {
                            it.parseQuestion(questions, options)
                        }
                    }
                    elasDao.insertQuestions(questions)
                    elasDao.insertOptions(options)
                }.doOnError {
                   Log.d("checke",it.toString())
                }.ignoreElement()
            }.subscribeOn(Schedulers.io())
    }

    fun Map<String, Any>.parseQuestion(questions: MutableList<QuestionData>, options: MutableList<OptionData>) {
        val question: String? = getValue("question_text").toString()
        val questionId: Long? = getValue("question_id").toString().toLong()
        var category: String? = getValue("category").toString()
        if(category == null) {
            category = "Miscellaneous"
        }
        if (question != null && questionId != null ) {
            questions.add(questionId.toInt(), QuestionData(questionId, question, category))
        } else {
            return
        }
        val numberOfOptions: Int? = getValue("total_options").toString().toInt()
        if (numberOfOptions != null) {
            for (i in 1 .. numberOfOptions) {
                val option: String? = getValue(i.toString()).toString()
                if (option != null)
                    options.add(parseOption(questionId, option))
            }
        } else {
            questions.removeAt(questionId.toInt())
            return
        }
    }

    /*fun Questions.toQuestionData():QuestionData{
         return QuestionData(qId,question,category)
    }

    fun Questions.toOptionsData():List<OptionData>
    {
         var options:List<OptionData> = emptyList()
        options.plus(OptionData(1,option1,qId))
        options= options.plus(OptionData(2,option2,qId))
        options= options.plus(OptionData(3,option3,qId))
        options= options.plus(OptionData(4,option4,qId))

        return options
    }*/
}