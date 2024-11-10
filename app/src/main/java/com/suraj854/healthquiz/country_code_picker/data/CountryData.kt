package com.suraj854.healthquiz.country_code_picker.data



import com.suraj854.healthquiz.R
import java.util.*

data class CountryData(
    private var cCodes: String,
    val countryPhoneCode: String = "+90",
    val cNames:String = "tr",
    val flagResID: Int = R.drawable.tr
) {
    val countryCode = cCodes.lowercase(Locale.getDefault())
}
