package com.suraj854.healthquiz.presentation.quiz

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.suraj854.healthquiz.presentation.theme.Blue
import com.suraj854.healthquiz.presentation.theme.DarkGray
import com.suraj854.healthquiz.presentation.theme.Green
import com.suraj854.healthquiz.presentation.theme.Green100
import com.suraj854.healthquiz.presentation.theme.Orange
import com.suraj854.healthquiz.presentation.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    var timerInitialTime by remember { mutableStateOf(20) }
    var isTimerRunning by remember { mutableStateOf(true) }
    var isTimerReset by remember { mutableStateOf(false) }


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
                painter = painterResource(id = R.drawable.app_background),
                contentScale = ContentScale.Fit,
                contentDescription = "splash_bg",
                modifier = Modifier.fillMaxSize()
            )
          /*  Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
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
                            .height(68.sdp),
                        contentDescription = "Logo"
                    )
                }
            }*/
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.sdp)

                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.sdp), horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ministry_01),
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .height(72.sdp)
                                    .width(120.dp).scale(1.1f),

                                contentDescription = "Logo"
                            )
                            Spacer(modifier = Modifier.width(8.sdp))
                            VerticalDivider(color = Color.Gray,modifier = Modifier.padding(vertical = 8.sdp))
                            Spacer(modifier = Modifier.width(8.sdp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(52.sdp),

                                contentDescription = "Logo"
                            )
                        }
                        /*
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(0.5f).padding(horizontal = 16.dp), horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.ministry_2),
                                                            contentScale = ContentScale.FillBounds,
                                                            modifier = Modifier
                                                                .height(72.sdp).width(130.dp).scale(1.2f),

                                                            contentDescription = "Logo"
                                                        )
                                                    }*/
                    }


                }
                Row(
                    modifier = Modifier
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

                Spacer(modifier = Modifier.height(4.sdp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 62.sdp)
                ) {
                    Row(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(18.sdp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LinearProgressIndicator(
                                progress = { currentQuizProgress.value },
                                strokeCap = StrokeCap.Round,
                                color = Blue,
                                trackColor = Blue.copy(alpha = 0.2f),
                                modifier = Modifier
                                    .width(320.sdp)
                                    .height(6.dp)
                            )
                            Spacer(modifier = Modifier.size(4.sdp))
                            CountdownTimerScreen(
                                onReset = { isReset ->
                                    isTimerReset = isReset
                                },
                                isTimerRunning = isTimerRunning,
                                initialTime = timerInitialTime,
                                isTimerReset = isTimerReset,
                                onTimeUp = { misTimerRunning ->
                                    if (questionList.value.get(currentQuestionIndex.value).selectedOptionID <= 0) {

                                        Toast.makeText(
                                            context,
                                            "Please choose the given options",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        isTimerRunning = misTimerRunning
                                        isTimerReset=false
                                        return@CountdownTimerScreen;
                                    }

                                    isTimerReset = true
                                    isTimerRunning = true

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


                                    }
                                })


                        }


                    }
                }

                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.sdp)

                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ), // Set the elevation here
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .padding(horizontal = 12.sdp)
                    ) {
                        Row(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = questionList.value.get(currentQuestionIndex.value).question_id.toString() + ". " + questionList.value[currentQuestionIndex.value].question,
                                style = TextStyle(
                                    fontSize = 14.ssp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }



                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2), modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 82.sdp, max = 92.sdp)
                        ) {
                            items(
                                questionList.value.get(currentQuestionIndex.value).options.size
                            ) { index ->
                                if (questionList.value.get(currentQuestionIndex.value).options.get(
                                        index
                                    ).option_value!="null"){
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
                        }
                        Box(modifier = Modifier.height(4.sdp))
                        Button(
                            onClick = {

                                if (questionList.value.get(currentQuestionIndex.value).selectedOptionID <= 0) {

                                    Toast.makeText(
                                        context,
                                        "Please choose the given options",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    isTimerRunning = false
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
                                    isTimerReset = true
                                    isTimerRunning=true
                                } else {
                                    viewModel.saveQuiz(context, questionList.value)
                                    viewModel.generateCertificate(context)


                                }


                            },
                            colors = ButtonDefaults.buttonColors(Orange),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .height(30.sdp)
                                .padding(start = 10.dp, bottom = 4.sdp, end = 10.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(
                                text = if (currentQuestionIndex.value < questionList.value.size - 1) {
                                    "Next"
                                } else {
                                    "Submit"
                                },
                                fontSize = 8.ssp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.sdp))
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


            }

        }
    } else if (isFormLoading.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier

                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_background),
                    contentScale = ContentScale.Fit,
                    contentDescription = "splash_bg",
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.height(52.dp),color = Green100)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Please wait",
                        textAlign = TextAlign.Center,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray,
                        )

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.sdp)

                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.sdp), horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ministry_01),
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .height(72.sdp)
                                    .width(120.dp).scale(1.1f),

                                contentDescription = "Logo"
                            )
                            Spacer(modifier = Modifier.width(8.sdp))
                            VerticalDivider(color = Color.Gray,modifier = Modifier.padding(vertical = 8.sdp))
                            Spacer(modifier = Modifier.width(8.sdp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(52.sdp),

                                contentDescription = "Logo"
                            )
                        }
                        /*
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(0.5f).padding(horizontal = 16.dp), horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.ministry_2),
                                                            contentScale = ContentScale.FillBounds,
                                                            modifier = Modifier
                                                                .height(72.sdp).width(130.dp).scale(1.2f),

                                                            contentDescription = "Logo"
                                                        )
                                                    }*/
                    }


                }
            }
        }


    }
}

@SuppressLint("DefaultLocale", "CoroutineCreationDuringComposition")
@Composable
fun CountdownTimerScreen(
    initialTime: Int = 20,
    onTimeUp: (Boolean) -> Unit,
    onReset: (Boolean) -> Unit,
    isTimerRunning: Boolean = true,
    isTimerReset: Boolean = false

) {


    var timeLeft by remember { mutableStateOf(initialTime) }
    if (isTimerReset) {
        timeLeft = initialTime
        onReset(false)


    }


    // Using LaunchedEffect to start and restart the countdown
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            onTimeUp(false)
            timeLeft = initialTime
            // Stop the timer
            // Restart the timer
        }
    }


    // Determine the text color based on the time left
    val textColor = if (timeLeft <= 10) Color.Red else DarkGray

    // Display the time left in "Time Left: 00:20" format with an icon
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(18.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_timer_icon),
                contentDescription = null,
                modifier = Modifier.size(12.sdp),
                colorFilter = ColorFilter.tint(textColor)
            )
            Spacer(modifier = Modifier.size(2.sdp))

            Text(
                text = "Time Left ${String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)}",
                fontSize = 8.ssp,
                color = textColor
            )
        }
    }
}