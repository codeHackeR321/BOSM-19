package com.dvm.appd.bosm.dbg.elas.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.elas.model.UIStateElas
import com.dvm.appd.bosm.dbg.elas.model.dataClasses.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.repo.ElasRepository
import com.dvm.appd.bosm.dbg.elas.model.retrofit.PlayerRankingResponse
import com.dvm.appd.bosm.dbg.shared.util.asMut

class ElasQuestionViewModel(val repository: ElasRepository): ViewModel() {

    var question: LiveData<List<CombinedQuestionOptionDataClass>> = MutableLiveData()
    var uiState: LiveData<UIStateElas> = MutableLiveData()
    var leaderboard: LiveData<List<PlayerRankingResponse>> = MutableLiveData()

    init {
        uiState.asMut().postValue(UIStateElas.Loading)
        getLeaderboard()
    }

    @SuppressLint("CheckResult")
    fun getQuestion(id: Long) {
        repository.getParticularQuestionFromRoom(id).subscribe({
            if (it != null) {
                question.asMut().postValue(it)
                uiState.asMut().postValue(UIStateElas.Questions(mapOf(Pair(it.first().questionId, it))))
            } else {
                uiState.asMut().postValue(UIStateElas.Failure("Question not found in local database"))
            }
        },{
            uiState.asMut().postValue(UIStateElas.Failure("Error in Local database. Please restart the app"))
            Log.e("QuestionViewModel", "Failed to get question from room = ${it.toString()}")
        })
    }

    @SuppressLint("CheckResult")
    fun submitAnswer(questionId: Long, optionId: Long) {
        uiState.asMut().postValue(UIStateElas.Loading)
        repository.submitAnswerForQuestion(questionId, optionId).subscribe({
            when(it.code()) {
                200 -> {
                    uiState.asMut().postValue(UIStateElas.Failure("${it.body().toString()}"))
                }
            }

        },{
            uiState.asMut().postValue(UIStateElas.Failure("Couldn't Submit your answer at this point. Try again later"))
        })
    }


    @SuppressLint("CheckResult")
    private fun getLeaderboard() {
        repository.getLeaderboardFromRoom().subscribe({
            Log.d("Elas VoewModel", "Observer for leaderboard entered with = ${it.toString()}")
            leaderboard.asMut().postValue(it)
        },{
            Log.e("ELASQViewModel", "Error in reading Leaderboard from room = ${it.message.toString()}")
            uiState.asMut().postValue(UIStateElas.Failure("Failed to recive data from server. Try Again"))
        })
    }

}