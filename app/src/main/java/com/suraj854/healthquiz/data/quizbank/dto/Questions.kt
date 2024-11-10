package com.suraj854.healthquiz.data.quizbank.dto

data class Questions(
    val correct_option: CorrectOption,
    val options: List<Option>,
    val question: String,
    val question_id: Int,
    var selectedOptionID: Int
)