package com.suraj854.healthquiz.domain.quiz.entities

import com.suraj854.healthquiz.data.quizbank.dto.Questions

data class QuizRecord(val userEmail: String, val quizQues: List<Questions>)