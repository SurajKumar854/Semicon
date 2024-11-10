package com.suraj854.healthquiz.data.common

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    // Name of the shared preference file
    private val PREFS_NAME = "user_prefs"

    // Keys for each data point
    private val KEY_FIRST_NAME = "first_name"
    private val KEY_LAST_NAME = "last_name"
    private val KEY_EMAIL = "email"
    private val KEY_COUNTRY_CODE = "country_code"
    private val KEY_PHONE_NUMBER = "phone_number"
    private val KEY_USER_SCORE = "quizScore"
    private val KEY_CERTIFICATE_URL = "certificate_url"

    // SharedPreferences instance
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Function to save user data
    fun saveUserData(
        firstName: String,
        lastName: String,
        email: String,
        countryCode: String,
        phoneNumber: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_FIRST_NAME, firstName)
        editor.putString(KEY_LAST_NAME, lastName)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_COUNTRY_CODE, countryCode)
        editor.putString(KEY_PHONE_NUMBER, phoneNumber)
        editor.apply() // Apply changes asynchronously
    }

    // Function to get first name
    fun getFirstName(): String? {
        return sharedPreferences.getString(KEY_FIRST_NAME, null)
    }

    fun saveCertificateUrl(url: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CERTIFICATE_URL, url)
        editor.apply()
    }

    fun getQuizScore(): Float? {
        return sharedPreferences.getFloat(KEY_USER_SCORE, 0f)
    }

    fun saveQuizScore(score: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(KEY_USER_SCORE, score)
        editor.apply()
    }

    // Function to get last name
    fun getLastName(): String? {
        return sharedPreferences.getString(KEY_LAST_NAME, null)
    }

    fun getCertificateUrl(): String? {
        return sharedPreferences.getString(KEY_CERTIFICATE_URL, null)
    }


    // Function to get email
    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    // Function to get country code
    fun getCountryCode(): String? {
        return sharedPreferences.getString(KEY_COUNTRY_CODE, null)
    }

    // Function to get phone number
    fun getPhoneNumber(): String? {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, null)
    }

    // Function to clear all user data
    fun clearUserData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
