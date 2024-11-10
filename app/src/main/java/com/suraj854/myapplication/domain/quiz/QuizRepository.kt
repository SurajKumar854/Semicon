package com.suraj854.myapplication.domain.quiz

import android.content.Context
import com.suraj854.myapplication.data.quizbank.dto.Questions
import com.suraj854.myapplication.data.quizbank.dto.QuizBankData
import com.suraj854.myapplication.data.quizbank.dto.QuizResponse
import kotlinx.coroutines.flow.Flow


interface QuizRepository {
    suspend fun loadQuiz(context: Context): Flow<QuizResponse>
    suspend fun saveQuiz(context: Context, quizResponse:  List<Questions>): Flow<Boolean>

    suspend fun saveCertificateUrlData(context: Context, email:String,url:String): Flow<Boolean>
}