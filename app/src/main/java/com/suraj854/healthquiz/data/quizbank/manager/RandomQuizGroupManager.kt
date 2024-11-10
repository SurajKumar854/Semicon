package com.suraj854.healthquiz.data.quizbank.manager

import android.content.Context
import android.content.SharedPreferences


class RandomQuizGroupManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("RandomIndexPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Keys for storing used indices
    private val USED_VALUES_KEY = "used_values"

    init {
        // Initialize the list of values if not already done
        if (sharedPreferences.getStringSet(USED_VALUES_KEY, null) == null) {
            initializeValues()
        }
    }

    // Initialize or reset the list of possible values
    private fun initializeValues() {
        val allValues = (0..4).map { it.toString() }.toMutableSet()
        editor.putStringSet(USED_VALUES_KEY, allValues).apply()
    }


    fun getRandomIndex(): Int? {
        val usedValues =
            sharedPreferences.getStringSet(USED_VALUES_KEY, mutableSetOf())?.toMutableSet()

        // Check if all values are used
        if (usedValues.isNullOrEmpty()) {
            // Reset the list of values if needed
            initializeValues()
            return getRandomIndex()
        }

        // Pick a random value
        val randomValue = usedValues.random()
        usedValues.remove(randomValue)

        // Update shared preferences
        editor.putStringSet(USED_VALUES_KEY, usedValues).apply()

        return randomValue.toInt()
    }
}