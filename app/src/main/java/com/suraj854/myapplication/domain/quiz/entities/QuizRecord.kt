package com.suraj854.myapplication.domain.quiz.entities

import com.suraj854.myapplication.data.quizbank.dto.Questions

data class QuizRecord(val userEmail: String, val quizQues: List<Questions>)