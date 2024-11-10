package com.suraj854.healthquiz.presentation.quiz

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.suraj854.healthquiz.R
import com.suraj854.healthquiz.core.MissionSemiConductorApiState
import com.suraj854.healthquiz.data.quizbank.dto.Questions
import com.suraj854.healthquiz.data.quizbank.dto.QuizBankData
import com.suraj854.healthquiz.navigation.Screen
import com.suraj854.healthquiz.presentation.common.BrandHeader
import com.suraj854.healthquiz.presentation.quiz.components.QuizOptionItem
import com.suraj854.healthquiz.presentation.quiz.viewmodel.QuizViewModel
import com.suraj854.healthquiz.presentation.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val context = LocalContext.current
    val isFormLoading = remember {
        mutableStateOf(false)
    }


    val questionList = remember { mutableStateOf(mutableListOf<Questions>()) }
    val currentQuestionIndex = remember {
        mutableStateOf(0)
    }
    val currentQuizProgress = remember {
        mutableStateOf(0.2f)
    }
    val questionOptionsList =
        mutableListOf(
            "Option 1",
            "Option 1",
            "Option 1",
            "Option 1"
        )
    var currentSelectedOption by remember {
        mutableStateOf(questionOptionsList[0])
    }

    LaunchedEffect(key1 = true) {
        viewModel.loadQuestionBank(context)
    }
    LaunchedEffect(key1 = true) {
        viewModel.response.collect { response ->

            when (response) {


                is MissionSemiConductorApiState.Success -> {
                    val resData =
                        (viewModel.response.value as MissionSemiConductorApiState.Success<QuizBankData?>).data

                    resData?.questions_list?.let { questionList.value.addAll(it) }


                    //    question.value = resData?.questions_list?.get(0)?.question ?: ""
                }


                else -> {

                }
            }

        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.quizSaveResponse.collect { response ->

            when (response) {
                is MissionSemiConductorApiState.Loading -> {
                    isFormLoading.value = true
                }

                is MissionSemiConductorApiState.Success -> {
                    val resData = response.data
                    if (resData == true) {
                        navHostController.navigate(Screen.QRCodeScreen.route) {
                            popUpTo(
                                navHostController.currentBackStackEntry?.destination?.id
                                    ?: -1
                            ) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Error occurred while generating certificate",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                    //    question.value = resData?.questions_list?.get(0)?.question ?: ""
                }


                else -> {

                }
            }

        }
    }




    if (questionList.value.isNotEmpty() && !isFormLoading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {


            Image(
                painter = painterResource(id = R.drawable.splash_bg),
                contentScale = ContentScale.FillBounds,
                contentDescription = "splash_bg",
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 52.dp),
            ) {


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
                            .scale(1.3f),
                        contentDescription = "Logo"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    BrandHeader()
                }
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Quiz",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = PrimaryColor,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = PrimaryColor,
                                shape = RoundedCornerShape(18)
                            )
                            .background(
                                color = PrimaryColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(18)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                LinearProgressIndicator(
                    progress = { currentQuizProgress.value },
                    strokeCap = StrokeCap.Round,
                    color = PrimaryColor,
                    trackColor = PrimaryColor.copy(alpha = 0.2f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .padding(horizontal = 24.dp),
                )
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
                        Row(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = questionList.value.get(currentQuestionIndex.value).question_id.toString() + ".",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Row(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = questionList.value[currentQuestionIndex.value].question,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        LazyColumn() {
                            items(
                                questionList.value.get(currentQuestionIndex.value).options.size
                            ) { index ->
                                QuizOptionItem(
                                    isEnable = questionList.value.get(currentQuestionIndex.value).options.get(
                                        index
                                    ).option_value == currentSelectedOption,
                                    name = questionList.value.get(
                                        currentQuestionIndex.value
                                    ).options.get(index).option_value,
                                    onChecked = {
                                        currentSelectedOption =
                                            questionList.value.get(currentQuestionIndex.value).options.get(
                                                index
                                            ).option_value

                                        questionList.value.get(currentQuestionIndex.value)
                                            .apply {
                                                selectedOptionID =
                                                    questionList.value.get(currentQuestionIndex.value).options.get(
                                                        index
                                                    ).option_id
                                            }
                                        Log.e(
                                            "Tag",
                                            questionList.value.get(currentQuestionIndex.value).selectedOptionID.toString()
                                        )

                                    })
                            }
                        }
                        Box(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {

                                if (questionList.value.get(currentQuestionIndex.value).selectedOptionID <= 0) {

                                    Toast.makeText(
                                        context,
                                        "Please choose the given options",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@Button
                                }


                                if (currentQuestionIndex.value < questionList.value.size - 1) {
                                    currentQuestionIndex.value++
                                    val currentProgressIndex = currentQuestionIndex.value + 1

                                    val currentProgress =
                                        ((currentProgressIndex / (questionList.value.size).toFloat()) * 100) / 100
                                    currentQuizProgress.value = currentProgress
                                    Log.e(
                                        "Progress",
                                        "${currentProgress}  ${currentQuestionIndex.value.toFloat()}    ${questionList.value.size}"

                                    ) // This will log 80.0

                                } else {
                                    viewModel.saveQuiz(context, questionList.value)
                                    viewModel.generateCertificate(context)
                                    /* isFormLoading.value = true


                                     val certificate = CertificateView(context = context)
                                     certificate.generatePdf() { isCompleted ->

                                         navHostController.navigate(Screen.QRCodeScreen.route) {
                                             popUpTo(
                                                 navHostController.currentBackStackEntry?.destination?.id
                                                     ?: -1
                                             ) {
                                                 inclusive = true
                                             }
                                         }
                                     }*/

                                }


                            },
                            colors = ButtonDefaults.buttonColors(Color(0xff1D61E7)),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 10.dp, bottom = 24.dp, end = 10.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(
                                text = if (currentQuestionIndex.value < questionList.value.size - 1) {
                                    "Next"
                                } else {
                                    "Submit"
                                },
                                modifier = Modifier.padding(vertical = 12.dp),
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = questionList.value.get(currentQuestionIndex.value).question_id.toString() + " of ${questionList.value.size} ",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryColor
                        )
                    )
                }
                /* LazyRow(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.Center
                 ) {
                     items(questionList.value.size) { index ->

                         Row(
                             modifier = Modifier
                                 .padding(horizontal = 8.dp)
                                 .background(
                                     color = if (index == currentQuestionIndex.value) {
                                         Color.White
                                     } else Color.Transparent, shape = CircleShape
                                 )
                                 .size(48.dp)
                                 .border(width = 2.dp, color = Color.White, shape = CircleShape),
                             verticalAlignment = Alignment.CenterVertically,
                             horizontalArrangement = Arrangement.Center
                         ) {

                             if (questionList.value[index].selectedOptionID > 0) {
                                 Icon(
                                     imageVector = Icons.Default.Check,
                                     contentDescription = "check",
                                     tint = Color.White
                                 )
                             } else {
                                 Text(
                                     text = questionList.value[index].question_id.toString(),
                                     style = TextStyle(
                                         fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                         fontWeight = FontWeight.Bold,
                                         color = if (index == currentQuestionIndex.value) {
                                             Color.Red
                                         } else Color.White
                                     )
                                 )
                             }


                         }
                     }
                 }*/


                /*  Card(
                      modifier = Modifier.padding(16.dp),
                      onClick = { },
                      colors = CardDefaults.cardColors(containerColor = Color.White)
                  ) {
                      Row(modifier = Modifier.padding(8.dp)) {
                          Text(
                              text = questionList.value.get(currentQuestionIndex.value).question_id.toString() + ".",
                              style = TextStyle(
                                  fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                  fontWeight = FontWeight.Bold
                              )
                          )
                      }
                      Column {
                          Row(modifier = Modifier.padding(16.dp)) {
                              Text(
                                  text = questionList.value[currentQuestionIndex.value].question,
                                  style = TextStyle(
                                      fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                      fontWeight = FontWeight.Bold
                                  )
                              )
                          }

                          LazyColumn() {
                              items(
                                  questionList.value.get(currentQuestionIndex.value).options.size
                              ) { index ->
                                  QuizOptionItem(
                                      isEnable = questionList.value.get(currentQuestionIndex.value).options.get(
                                          index
                                      ).option_value == currentSelectedOption,
                                      name = " ${index + 1}. ${
                                          questionList.value.get(
                                              currentQuestionIndex.value
                                          ).options.get(index).option_value
                                      }",
                                      onChecked = {
                                          currentSelectedOption =
                                              questionList.value.get(currentQuestionIndex.value).options.get(
                                                  index
                                              ).option_value

                                          questionList.value.get(currentQuestionIndex.value).apply {
                                              selectedOptionID =
                                                  questionList.value.get(currentQuestionIndex.value).options.get(
                                                      index
                                                  ).option_id
                                          }
                                          Log.e(
                                              "Tag",
                                              questionList.value.get(currentQuestionIndex.value).selectedOptionID.toString()
                                          )
                                      }
                                  )
                              }
                          }
                          Box(modifier = Modifier.height(16.dp))

                      }

                  }*/


            }

        }
    } else if (isFormLoading.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier

                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash_bg),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "splash_bg",
                    modifier = Modifier.fillMaxSize()
                )

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
                Column(modifier = Modifier.padding(top = 24.dp)) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        BrandHeader()
                    }

                }
            }
        }


    }
}

fun convertToFraction(value: Float, divisor: Float): Float {
    return value / divisor
}