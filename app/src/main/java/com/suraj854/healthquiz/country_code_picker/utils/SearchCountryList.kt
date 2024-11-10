package com.zvoid_india.locatify.country_code_picker.utils

import android.content.Context
import com.suraj854.healthquiz.country_code_picker.data.CountryData
import com.suraj854.healthquiz.country_code_picker.data.utils.getCountryName

fun List<CountryData>.searchCountry(key: String, context: Context): MutableList<CountryData> {
    val tempList = mutableListOf<CountryData>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}