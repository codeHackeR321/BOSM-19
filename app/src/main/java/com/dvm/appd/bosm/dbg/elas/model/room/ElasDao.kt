package com.dvm.appd.bosm.dbg.elas.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.elas.model.CombinedQuestionOptionDataClass
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ElasDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuestions(questions: List<QuestionData>)

    @Insert
    fun insertOptions(options: List<OptionData>)

    @Query("DELETE FROM option_table WHERE questionId = :id")
    fun deleteOptionsForQuestionWithId(id: Long)

    @Query("DELETE FROM option_table")
    fun deleteAllOptions()

    @Query("SELECT * FROM question_table JOIN option_table WHERE category = :category ")
    fun selectQuestionsInCategory(category: String): Flowable<List<CombinedQuestionOptionDataClass>>

    @Query("SELECT * FROM question_table JOIN option_table")
    fun getAllQuestions(): Flowable<List<CombinedQuestionOptionDataClass>>
}