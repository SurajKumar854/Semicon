package com.suraj854.myapplication.country_code_picker.component

import android.health.connect.datatypes.units.Length
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suraj854.myapplication.country_code_picker.data.utils.checkPhoneNumber
import com.suraj854.myapplication.country_code_picker.data.utils.getDefaultLangCode
import com.suraj854.myapplication.country_code_picker.data.utils.getDefaultPhoneCode
import com.suraj854.myapplication.presentation.theme.PrimaryColor
import com.zvoid_india.locatify.country_code_picker.component.TogiCodeDialog
import com.suraj854.myapplication.country_code_picker.data.utils.getLibCountries
import com.suraj854.myapplication.R

import com.zvoid_india.locatify.country_code_picker.transformation.PhoneNumberTransformation
import kotlinx.coroutines.launch


private var fullNumberState: String by mutableStateOf("")
private var checkNumberState: Boolean by mutableStateOf(false)
private var phoneNumberState: String by mutableStateOf("")
private var countryCodeState: String by mutableStateOf("")

private var countryCodeValueState: String by mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ZvoidCountryCodePicker(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    onPhoneCodeChange: (String) -> Unit,
    shape: Shape = RoundedCornerShape(24.dp),
    color: Color = MaterialTheme.colorScheme.background,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    maxLength: Int = 10,
    focusedBorderColor: Color = Color.Transparent,
    unfocusedBorderColor: Color = Color.Transparent,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    bottomStyle: Boolean = false
) {
    val context = LocalContext.current
    var textFieldValue by rememberSaveable { mutableStateOf(text) }

    var phoneCode by rememberSaveable {
        mutableStateOf(getDefaultPhoneCode(context))
    }
    var defaultLang by rememberSaveable {
        mutableStateOf(
            getDefaultLangCode(context)
        )
    }

    val focusRequester = remember { FocusRequester() }
    fullNumberState = phoneCode + textFieldValue
    phoneNumberState = textFieldValue
    countryCodeState = defaultLang
    countryCodeValueState = phoneCode
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    Surface(color = color) {
        Column() {
            if (bottomStyle) {
                TogiCodeDialog(
                    pickedCountry = {
                        phoneCode = it.countryPhoneCode
                        defaultLang = it.countryCode
                        onPhoneCodeChange(it.countryPhoneCode)
                    },
                    defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                    showCountryCode = showCountryCode,
                    showFlag = showCountryFlag,
                    showCountryName = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {

                if (!bottomStyle)
                    Row {
                        Spacer(modifier = Modifier.width(2.dp))
                        Column {
                            TogiCodeDialog(
                                pickedCountry = {
                                    phoneCode = it.countryPhoneCode
                                    defaultLang = it.countryCode
                                    onPhoneCodeChange(it.countryPhoneCode)
                                },
                                defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                                showCountryCode = showCountryCode,
                                showFlag = showCountryFlag
                            )
                        }

                    }
                Spacer(modifier = Modifier.width(10.dp))
                VerticalDivider(Modifier.height(52.dp))
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = {

                        if (it.length <= maxLength) textFieldValue = it
                        onValueChange(it)
                    },
                    decorationBox = { innerTextField ->
                        // Place the innerTextField inside the box for proper alignment
                        if(textFieldValue.isBlank()){
                            Text(text = "Enter your Phone number",
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(color = Color.Gray.copy(alpha = 0.5f), fontSize = 18.sp)
                            )
                        }
                        innerTextField()
                    },
                    textStyle = TextStyle(fontSize = 18.sp),
                    visualTransformation = PhoneNumberTransformation(getLibCountries.single { it.countryCode == defaultLang }.countryCode.uppercase()),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword,
                        autoCorrect = true,
                        imeAction = ImeAction.Done // Sets the action button to "Done"
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusRequester.requestFocus()
                        keyboardController?.hide()
                    }),

                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .padding(16.dp) // Padding around text inside the field

                )
            }
            /*   TextField(
                   modifier = modifier.fillMaxWidth(),
                   shape = shape,
                   value = textFieldValue,
                   onValueChange = {
                       textFieldValue = it
                       if (text != it) {
                           onValueChange(it)
                       }
                   },
                   textStyle = TextStyle(
                       color = PrimaryColor,
                       fontSize = 24.sp,
                       fontWeight = FontWeight.W600

                   ),
                   singleLine = true,
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = if (getErrorStatus()) Color.Red else focusedBorderColor,
                       unfocusedBorderColor = if (getErrorStatus()) Color.Red else unfocusedBorderColor,
                       cursorColor = cursorColor
                   ),
                   visualTransformation = PhoneNumberTransformation(getLibCountries.single { it.countryCode == defaultLang }.countryCode.uppercase()),
                   placeholder = {
                       Text(
                           modifier = Modifier.fillMaxWidth(),
                           text = "Enter number",
                           color = Color.LightGray,
                           fontSize = 24.sp,
                           fontWeight = FontWeight.W600
                       )
                   },
                   keyboardOptions = KeyboardOptions.Default.copy(
                       keyboardType = KeyboardType.NumberPassword,
                       autoCorrect = true,
                   ),
                   keyboardActions = KeyboardActions(onDone = {
                       keyboardController?.hideSoftwareKeyboard()
                   }),
                   maxLines = 10,
                   leadingIcon = {
                       if (!bottomStyle)
                           Row {
                               Column {
                                   TogiCodeDialog(
                                       pickedCountry = {
                                           phoneCode = it.countryPhoneCode
                                           defaultLang = it.countryCode
                                           onPhoneCodeChange(it.countryPhoneCode)
                                       },
                                       defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                                       showCountryCode = showCountryCode,
                                       showFlag = showCountryFlag
                                   )
                               }
                           }
                   },
               )
           }*/
            /* if (getErrorStatus()) Text(
                 text = stringResource(id = R.string.invalid_number),
                 color = MaterialTheme.colorScheme.error,
                 style = MaterialTheme.typography.labelSmall,
                 fontWeight = FontWeight.Bold,
                 modifier = Modifier.padding(top = 0.8.dp)
             )*/
        }
    }
}

fun getFullPhoneNumber(): String {
    return fullNumberState
}

fun getOnlyPhoneNumber(): String {
    return phoneNumberState
}

fun getErrorStatus(): Boolean {
    return checkNumberState
}

fun isPhoneNumber(): Boolean {
    val check = checkPhoneNumber(
        phone = phoneNumberState, fullPhoneNumber = fullNumberState, countryCode = countryCodeState
    )
    checkNumberState = check
    return check
}

fun getCountryCode(): String {
    return countryCodeValueState
}