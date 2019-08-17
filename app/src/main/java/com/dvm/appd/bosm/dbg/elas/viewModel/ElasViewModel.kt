package com.dvm.appd.bosm.dbg.elas.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.elas.model.CombinedQuestionOptionDataClass
import com.dvm.appd.bosm.dbg.elas.model.QuestionWithAllOptionsData
import com.dvm.appd.bosm.dbg.elas.model.repo.ElasRepository
import io.reactivex.disposables.CompositeDisposable

class ElasViewModel(val elasRepository: ElasRepository) : ViewModel() {
    private val TAG = "ELAS REPO"

    var questions: LiveData<Map<Long, List<CombinedQuestionOptionDataClass>>> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()

    init {
        getQuestions()
    }

    private fun getQuestions() {
        val d1 = elasRepository.getQuestionsFromRoom().doOnNext{
            Log.d(TAG, "Entered Observer with list = ${it.toString()}")
            (questions as MutableLiveData<Map<Long, List<CombinedQuestionOptionDataClass>>>).postValue(it.groupBy { it.questionId })
        }.doOnError{
            Log.e(TAG, "Error in reading from room = ${it.message.toString()}")
            reinitializeSubscription()
        }.subscribe()
        compositeDisposable.add(d1)
    }

    private fun reinitializeSubscription() {
        compositeDisposable.dispose()
        getQuestions()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}