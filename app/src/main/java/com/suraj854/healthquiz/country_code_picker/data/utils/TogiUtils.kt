package com.suraj854.healthquiz.country_code_picker.data.utils

import android.content.Context
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.suraj854.healthquiz.country_code_picker.data.CountryData

fun getDefaultLangCode(context: Context): String {
    val defaultCountry = "in"
    return defaultCountry
}

fun getDefaultPhoneCode(context: Context): String {
    val defaultCountry = getDefaultLangCode(context)
    val defaultCode: CountryData = getLibCountries.first { it.countryCode == defaultCountry }
    return defaultCode.countryPhoneCode.ifBlank { "+90" }
}

fun checkPhoneNumber(phone: String, fullPhoneNumber: String, countryCode: String): Boolean {
    val number: Phonenumber.PhoneNumber?
    if (phone.length > 6) {
        return try {
            number = PhoneNumberUtil.getInstance().parse(
                fullPhoneNumber,
                Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name
            )
            !PhoneNumberUtil.getInstance().isValidNumberForRegion(number, countryCode.uppercase())
        } catch (ex: Exception) {
            true
        }
    }
    return true
}
