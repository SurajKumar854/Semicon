package com.suraj854.healthquiz.domain.signup.entities

data class User(

    val firstname: String,
    val lastname: String,
    val email: String,
    val country_code: String,
    val phone: String,
    val createdAt: com.google.firebase.Timestamp,
    val updatedAt: com.google.firebase.Timestamp
)