package com.suraj854.healthquiz.presentation.signup.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.suraj854.healthquiz.R
import com.suraj854.healthquiz.core.MissionSemiConductorApiState
import com.suraj854.healthquiz.country_code_picker.component.ZvoidCountryCodePicker
import com.suraj854.healthquiz.data.common.Utils
import com.suraj854.healthquiz.data.signup.enum.AuthErrorType
import com.suraj854.healthquiz.domain.signup.entities.User
import com.suraj854.healthquiz.navigation.Screen
import com.suraj854.healthquiz.presentation.common.BrandHeader
import com.suraj854.healthquiz.presentation.signup.screen.viewmodel.SignUpViewModel
import com.suraj854.healthquiz.presentation.theme.BGColor
import com.suraj854.healthquiz.presentation.theme.PrimaryColor
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {


    val firstName = rememberSaveable { mutableStateOf("") }
    val firstNameError = rememberSaveable {
        mutableStateOf("Please enter your first name.")
    }
    val isFirstNameValid = remember {
        mutableStateOf(false)
    }
    val lastName = rememberSaveable { mutableStateOf("") }

    val lastNameError = rememberSaveable {
        mutableStateOf("Please enter your last name.")
    }
    val isLastNameValid = remember {
        mutableStateOf(false)
    }
    val formLoading = remember {
        mutableStateOf(false)
    }
    val apiErrorMsg = rememberSaveable {
        mutableStateOf("")
    }
    val isApiResponseSuccess = rememberSaveable {
        mutableStateOf(true)
    }

    val email = rememberSaveable { mutableStateOf("") }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val countryCode = rememberSaveable { mutableStateOf("") }
    val isEmailValid by derivedStateOf {
        email.value.isNotBlank() && Utils.isValidEmail(email.value)
    }
    val focusRequester = remember { FocusRequester() }
    val emailError = rememberSaveable {
        mutableStateOf("Please enter valid email.")
    }
    val isEmailValueValid = remember {
        mutableStateOf(false)
    }
    val isFormValid by derivedStateOf {
        phoneNumber.value.isNotBlank() && phoneNumber.value.length >= 10 && isEmailValid

    }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(key1 = true) {

        signUpViewModel.response.collect { response ->
            when (response) {
                is MissionSemiConductorApiState.NetworkError -> {
                    formLoading.value = false
                    apiErrorMsg.value = response.msg
                    isApiResponseSuccess.value = true
                    Log.e("Tag","Unknown Error Occurred-NetworkError ${response}")
                }

                is MissionSemiConductorApiState.Loading -> {
                    formLoading.value = true
                    isFirstNameValid.value = false
                    isLastNameValid.value = false
                    isApiResponseSuccess.value = true
                }

                is MissionSemiConductorApiState.Failure -> {
                    formLoading.value = false
                    Log.e("Tag","Unknown Error Occurred")


                }

                is MissionSemiConductorApiState.Success -> {

                    val response = response.data
                    if (response != null) {
                        val status = response.status
                        val errorType = response.errorType;
                        when (errorType) {
                            AuthErrorType.Email -> {
                                formLoading.value = false
                                apiErrorMsg.value = "Email already registered"
                                isApiResponseSuccess.value = false
                                Log.e("Tag","Unknown Error Occurred-Email ${response}")

                            }

                            AuthErrorType.MobileNumber -> {
                                formLoading.value = false
                                apiErrorMsg.value = "Phone number already registered"
                                isApiResponseSuccess.value = false
                                Log.e("Tag","Unknown Error Occurred-MobileNumber ${response}")
                            }

                            AuthErrorType.NoError -> {
                                Toast.makeText(
                                    context,
                                    "User registered successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                                navHostController.navigate(Screen.QuizScreen.route) {
                                    popUpTo(
                                        navHostController.currentBackStackEntry?.destination?.id
                                            ?: -1
                                    ) {
                                        inclusive = true
                                    }
                                }
                            }
                        }


                    }
                }

                else -> {
                    formLoading.value = false
                    isApiResponseSuccess.value = true
                    Log.e("Tag","Unknown Error Occurred-else ${response}")
                }
            }
        }


    }
    Scaffold(
        containerColor = BGColor,
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.splash_bg),
                contentScale = ContentScale.FillBounds,
                contentDescription = "splash_bg",
                modifier = Modifier.fillMaxSize()
            )
            if (formLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.height(52.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Please wait",
                        textAlign = TextAlign.Center,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor,

                        )

                }
            } else {



                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 24.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        BrandHeader()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp
                        ), // Set the elevation here
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Register",
                                    textAlign = TextAlign.Center,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xff758FF5),

                                    )
                            }
                            Text(
                                text = "First Name",
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                color = Color(0xff6C7278),

                                )

                            Spacer(modifier = Modifier.height(2.dp))
                            BasicTextField(
                                value = firstName.value,
                                onValueChange = { firstName.value = it },
                                decorationBox = { innerTextField ->
                                    // Place the innerTextField inside the box for proper alignment
                                    if (firstName.value.isBlank()) {
                                        Text(
                                            text = "Enter your first name",
                                            modifier = Modifier.fillMaxWidth(),
                                            style = TextStyle(
                                                color = Color.Gray.copy(alpha = 0.5f),
                                                fontSize = 18.sp
                                            )
                                        )
                                    }
                                    innerTextField()
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    autoCorrect = true,
                                    imeAction = ImeAction.Done // Sets the action button to "Done"
                                ),
                                keyboardActions = KeyboardActions(onDone = {
                                    focusRequester.requestFocus()
                                    keyboardController?.hide()
                                }),
                                textStyle = TextStyle(fontSize = 18.sp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp) // Padding around text inside the field

                            )
                            if (isFirstNameValid.value) {
                                Text(
                                    text = firstNameError.value,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    color = Color.Red,

                                    )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Last Name",
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                color = Color(0xff6C7278),

                                )

                            Spacer(modifier = Modifier.height(2.dp))
                            BasicTextField(
                                value = lastName.value,
                                onValueChange = { lastName.value = it },
                                decorationBox = { innerTextField ->
                                    // Place the innerTextField inside the box for proper alignment
                                    if (lastName.value.isBlank()) {
                                        Text(
                                            text = "Enter your last name",
                                            modifier = Modifier.fillMaxWidth(),
                                            style = TextStyle(
                                                color = Color.Gray.copy(alpha = 0.5f),
                                                fontSize = 18.sp
                                            )
                                        )
                                    }
                                    innerTextField()
                                },
                                textStyle = TextStyle(fontSize = 18.sp),
                                keyboardOptions = KeyboardOptions.Default.copy(
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
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp) // Padding around text inside the field

                            )
                            if (isLastNameValid.value) {
                                Text(
                                    text = lastNameError.value,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    color = Color.Red,

                                    )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Email",
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                color = Color(0xff6C7278),

                                )

                            Spacer(modifier = Modifier.height(2.dp))

                            BasicTextField(
                                value = email.value,
                                onValueChange = {
                                    email.value = it
                                    if (Utils.isValidEmail(it)) {
                                        isEmailValueValid.value = false
                                    } else {
                                        isEmailValueValid.value = true
                                    }
                                },
                                decorationBox = { innerTextField ->
                                    // Place the innerTextField inside the box for proper alignment
                                    if (email.value.isBlank()) {
                                        Text(
                                            text = "Enter your email address",
                                            modifier = Modifier.fillMaxWidth(),
                                            style = TextStyle(
                                                color = Color.Gray.copy(alpha = 0.5f),
                                                fontSize = 18.sp
                                            )
                                        )
                                    }
                                    innerTextField()
                                },
                                textStyle = TextStyle(fontSize = 18.sp),
                                keyboardOptions = KeyboardOptions.Default.copy(
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
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(16.dp) // Padding around text inside the field

                            )
                            if (isEmailValueValid.value) {
                                Text(
                                    text = emailError.value,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    color = Color.Red,

                                    )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Phone number",
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                color = Color(0xff6C7278),

                                )

                            Spacer(modifier = Modifier.height(2.dp))
                            ZvoidCountryCodePicker(
                                text = phoneNumber.value,
                                onValueChange = {
                                    phoneNumber.value = it
                                },
                                onPhoneCodeChange = {
                                    countryCode.value = it
                                },
                                unfocusedBorderColor = Color.Gray,
                                bottomStyle = false, //  if true the text-field is below the country code selector at the top.
                                shape = RoundedCornerShape(0.dp),
                                showCountryCode = true,
                                color = Color.Transparent,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {

                                    coroutineScope.launch {
                                        if (firstName.value.isBlank()) {
                                            isFirstNameValid.value = true
                                            return@launch
                                        }
                                        if (lastName.value.isBlank()) {
                                            isLastNameValid.value = true
                                            return@launch
                                        }
                                        if (email.value.isBlank()) {
                                            isEmailValueValid.value = true
                                            return@launch
                                        }
                                        signUpViewModel.signUpUser(
                                            context,
                                            User(
                                                firstname = firstName.value,
                                                lastname = lastName.value,
                                                email = email.value,
                                                country_code = if (countryCode.value.isEmpty()) "91" else countryCode.value,
                                                phone = phoneNumber.value,
                                                createdAt = Timestamp.now(),
                                                updatedAt = Timestamp.now()
                                            )
                                        )


                                    }


                                },
                                colors = ButtonDefaults.buttonColors(Color(0xff1D61E7)),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(start = 10.dp, bottom = 24.dp, end = 10.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                enabled = isFormValid
                            ) {
                                Text(
                                    text = "Register",
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                )
                            }

                            /*   TextField(modifier = Modifier
                                   .fillMaxWidth()
                                   .bringIntoViewRequester(bringIntoViewRequester)
                                   .onFocusEvent {
                                       if (it.isFocused) {
                                           coroutineScope.launch {
                                               bringIntoViewRequester.bringIntoView()
                                           }
                                       }
                                   }, value = firstName.value, onValueChange = {
                                   firstName.value = it
                               }, textStyle = TextStyle(
                                   color = PrimaryColor,
                                   fontSize = 18.sp,
                                   fontWeight = FontWeight.W600,
                                   textAlign = TextAlign.Left
                               ), colors = TextFieldDefaults.  .colors(

                                   focusedContainerColor = Color(0xffEDF1F3),
                                   unfocusedContainerColor = Color(0xffEDF1F3),
                                   disabledContainerColor = Color.Transparent,
                                   errorContainerColor = Color.Transparent,

                                   ), singleLine = true, placeholder = {
                                   if (firstName.value.isBlank()) {
                                       Text(
                                           modifier = Modifier.fillMaxWidth(),
                                           text = "Enter first name",
                                           color = Color.DarkGray,
                                           fontSize = 18.sp,
                                           textAlign = TextAlign.Left
                                       )
                                   }
                               })*/
                            /*    if (isFirstNameValid.value) {
                                    Text(
                                        text = firstNameError.value,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Red,

                                        )
                                }


                                Text(
                                    text = "Last Name",
                                    textAlign = TextAlign.Center,
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryColor,
                                    modifier = Modifier.padding(top = 16.dp)
                                )

                                TextField(modifier = Modifier
                                    .fillMaxWidth()
                                    .bringIntoViewRequester(bringIntoViewRequester)
                                    .onFocusEvent {
                                        if (it.isFocused) {
                                            coroutineScope.launch {
                                                bringIntoViewRequester.bringIntoView()
                                            }
                                        }
                                    }, value = lastName.value, onValueChange = {
                                    lastName.value = it
                                }, textStyle = TextStyle(
                                    color = PrimaryColor,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W600,
                                    textAlign = TextAlign.Left
                                ), colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = PrimaryColor,
                                    unfocusedBorderColor = PrimaryColor,
                                    cursorColor = PrimaryColor
                                ), singleLine = true, placeholder = {
                                    if (lastName.value.isBlank()) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "Enter last name",
                                            color = Color.DarkGray,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left
                                        )
                                    }
                                })
                                if (isLastNameValid.value) {
                                    Text(
                                        text = lastNameError.value,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Red,

                                        )
                                }

                                Text(
                                    text = "Email",
                                    textAlign = TextAlign.Center,
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryColor,
                                    modifier = Modifier.padding(top = 16.dp)
                                )

                                TextField(modifier = Modifier
                                    .fillMaxWidth()
                                    .bringIntoViewRequester(bringIntoViewRequester)
                                    .onFocusEvent {
                                        if (it.isFocused) {
                                            coroutineScope.launch {
                                                bringIntoViewRequester.bringIntoView()
                                            }
                                        }
                                    }, value = email.value, onValueChange = {
                                    email.value = it
                                    if (Utils.isValidEmail(it)) {
                                        isEmailValueValid.value = false
                                    } else {
                                        isEmailValueValid.value = true
                                    }
                                }, textStyle = TextStyle(
                                    color = PrimaryColor,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W600,
                                    textAlign = TextAlign.Left
                                ), colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = PrimaryColor,
                                    unfocusedBorderColor = PrimaryColor,
                                    cursorColor = PrimaryColor
                                ), singleLine = true, placeholder = {
                                    if (email.value.isBlank()) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "Enter email",
                                            color = Color.DarkGray,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left
                                        )
                                    }
                                })

                                if (isEmailValueValid.value) {
                                    Text(
                                        text = emailError.value,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Red,

                                        )
                                }

                                Text(
                                    text = "Phone number",
                                    textAlign = TextAlign.Center,
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryColor,
                                    modifier = Modifier.padding(top = 16.dp)
                                )

                                ZvoidCountryCodePicker(
                                    text = phoneNumber.value,
                                    onValueChange = {
                                        phoneNumber.value = it
                                    },
                                    onPhoneCodeChange = {
                                        countryCode.value = it
                                    },
                                    unfocusedBorderColor = Color.Transparent,
                                    bottomStyle = false, //  if true the text-field is below the country code selector at the top.
                                    shape = RoundedCornerShape(0.dp),
                                    showCountryCode = true,
                                    color = Color.Transparent,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                        }


                        *//*      Text(
                              text = "First Name",
                              textAlign = TextAlign.Center,
                              fontSize = 21.sp,
                              fontWeight = FontWeight.Bold,
                              color = PrimaryColor,

                              )

                          TextField(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .bringIntoViewRequester(bringIntoViewRequester)
                                  .onFocusEvent {
                                      if (it.isFocused) {
                                          coroutineScope.launch {
                                              bringIntoViewRequester.bringIntoView()
                                          }
                                      }
                                  },
                              value = firstName.value,
                              onValueChange = {
                                  firstName.value = it
                              },
                              textStyle = TextStyle(
                                  color = PrimaryColor,
                                  fontSize = 18.sp,
                                  fontWeight = FontWeight.W600,
                                  textAlign = TextAlign.Left
                              ),
                              colors = TextFieldDefaults.outlinedTextFieldColors(
                                  focusedBorderColor = PrimaryColor,
                                  unfocusedBorderColor = PrimaryColor,
                                  cursorColor = PrimaryColor
                              ),
                              singleLine = true,
                              placeholder = {
                                  if (firstName.value.isBlank()) {
                                      Text(
                                          modifier = Modifier.fillMaxWidth(),
                                          text = "Enter first name",
                                          color = Color.DarkGray,
                                          fontSize = 18.sp,
                                          textAlign = TextAlign.Left
                                      )
                                  }
                              })
                          if (isFirstNameValid.value) {
                              Text(
                                  text = firstNameError.value,
                                  textAlign = TextAlign.Center,
                                  fontSize = 12.sp,
                                  fontWeight = FontWeight.Bold,
                                  color = Color.Red,

                                  )
                          }


                          Text(
                              text = "Last Name",
                              textAlign = TextAlign.Center,
                              fontSize = 21.sp,
                              fontWeight = FontWeight.Bold,
                              color = PrimaryColor,
                              modifier = Modifier.padding(top = 16.dp)
                          )

                          TextField(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .bringIntoViewRequester(bringIntoViewRequester)
                                  .onFocusEvent {
                                      if (it.isFocused) {
                                          coroutineScope.launch {
                                              bringIntoViewRequester.bringIntoView()
                                          }
                                      }
                                  },
                              value = lastName.value,
                              onValueChange = {
                                  lastName.value = it
                              },
                              textStyle = TextStyle(
                                  color = PrimaryColor,
                                  fontSize = 18.sp,
                                  fontWeight = FontWeight.W600,
                                  textAlign = TextAlign.Left
                              ),
                              colors = TextFieldDefaults.outlinedTextFieldColors(
                                  focusedBorderColor = PrimaryColor,
                                  unfocusedBorderColor = PrimaryColor,
                                  cursorColor = PrimaryColor
                              ),
                              singleLine = true,
                              placeholder = {
                                  if (lastName.value.isBlank()) {
                                      Text(
                                          modifier = Modifier.fillMaxWidth(),
                                          text = "Enter last name",
                                          color = Color.DarkGray,
                                          fontSize = 18.sp,
                                          textAlign = TextAlign.Left
                                      )
                                  }
                              })
                          if (isLastNameValid.value) {
                              Text(
                                  text = lastNameError.value,
                                  textAlign = TextAlign.Center,
                                  fontSize = 12.sp,
                                  fontWeight = FontWeight.Bold,
                                  color = Color.Red,

                                  )
                          }

                          Text(
                              text = "Email",
                              textAlign = TextAlign.Center,
                              fontSize = 21.sp,
                              fontWeight = FontWeight.Bold,
                              color = PrimaryColor,
                              modifier = Modifier.padding(top = 16.dp)
                          )

                          TextField(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .bringIntoViewRequester(bringIntoViewRequester)
                                  .onFocusEvent {
                                      if (it.isFocused) {
                                          coroutineScope.launch {
                                              bringIntoViewRequester.bringIntoView()
                                          }
                                      }
                                  },
                              value = email.value,
                              onValueChange = {
                                  email.value = it
                                  if (Utils.isValidEmail(it)) {
                                      isEmailValueValid.value = false
                                  } else {
                                      isEmailValueValid.value = true
                                  }
                              },
                              textStyle = TextStyle(
                                  color = PrimaryColor,
                                  fontSize = 18.sp,
                                  fontWeight = FontWeight.W600,
                                  textAlign = TextAlign.Left
                              ),
                              colors = TextFieldDefaults.outlinedTextFieldColors(
                                  focusedBorderColor = PrimaryColor,
                                  unfocusedBorderColor = PrimaryColor,
                                  cursorColor = PrimaryColor
                              ),
                              singleLine = true,
                              placeholder = {
                                  if (email.value.isBlank()) {
                                      Text(
                                          modifier = Modifier.fillMaxWidth(),
                                          text = "Enter email",
                                          color = Color.DarkGray,
                                          fontSize = 18.sp,
                                          textAlign = TextAlign.Left
                                      )
                                  }
                              })

                          if (isEmailValueValid.value) {
                              Text(
                                  text = emailError.value,
                                  textAlign = TextAlign.Center,
                                  fontSize = 12.sp,
                                  fontWeight = FontWeight.Bold,
                                  color = Color.Red,

                                  )
                          }

                          Text(
                              text = "Phone number",
                              textAlign = TextAlign.Center,
                              fontSize = 21.sp,
                              fontWeight = FontWeight.Bold,
                              color = PrimaryColor,
                              modifier = Modifier.padding(top = 16.dp)
                          )

                          ZvoidCountryCodePicker(
                              text = phoneNumber.value,
                              onValueChange = {
                                  phoneNumber.value = it
                              },
                              onPhoneCodeChange = {
                                  countryCode.value = it
                              },
                              unfocusedBorderColor = Color.Transparent,
                              bottomStyle = false, //  if true the text-field is below the country code selector at the top.
                              shape = RoundedCornerShape(0.dp),
                              showCountryCode = true,
                              color = Color.Transparent,
                              modifier = Modifier
                                  .fillMaxWidth()
                          )
      *//*
                    Divider()
                    Column(Modifier.height(20.dp)) {

                    }*/


                            if (!isApiResponseSuccess.value) {
                                Row(horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = apiErrorMsg.value,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Red,

                                        )
                                }

                            }


                        }

                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .padding(bottom = 52.dp),
                    ) {
                        /*  Row(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .weight(0.5f),
                              horizontalArrangement = Arrangement.Center
                          ) {
                              Image(
                                  painter = painterResource(id = R.drawable.ministry),
                                  contentScale = ContentScale.Fit,
                                  modifier = Modifier
                                      .height(68.dp)
                                      .scale(1.6f),

                                  contentDescription = "Logo"
                              )

                          }*/

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f), horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_digital_india),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(68.dp)
                                    .scale(1.4f),
                                contentDescription = "Logo"
                            )
                        }
                    }

                }
            }

        }
    }
}
