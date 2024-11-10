package com.suraj854.myapplication.domain.quiz.usecase

import android.content.Context
import com.suraj854.myapplication.data.quizbank.dto.Questions
import com.suraj854.myapplication.data.quizbank.dto.QuizResponse
import com.suraj854.myapplication.domain.quiz.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizAuthUseCase @Inject constructor(private val quizRepository: QuizRepository) {
    suspend fun executeQuizBank(context: Context): Flow<QuizResponse?> {
        return quizRepository.loadQuiz(context)
    }

    suspend fun executeSaveQuizData(
        context: Context,
        quizBankData: List<Questions>
    ): Flow<Boolean> {
        return quizRepository.saveQuiz(context, quizBankData)
    }

    suspend fun executeSaveCertificateData(
        context: Context,
        email: String,
        url: String
    ): Flow<Boolean> {
        return quizRepository.saveCertificateUrlData(context = context, email = email, url = url)
    }
}