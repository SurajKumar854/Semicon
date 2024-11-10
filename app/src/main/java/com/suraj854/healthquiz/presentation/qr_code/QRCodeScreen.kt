package com.suraj854.healthquiz.presentation.qr_code

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
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
import com.suraj854.healthquiz.data.common.UserPreferences
import com.suraj854.healthquiz.data.common.Utils.generateQRCodeBitmap
import com.suraj854.healthquiz.navigation.Screen
import com.suraj854.healthquiz.presentation.common.BrandHeader
import com.suraj854.healthquiz.presentation.qr_code.component.CertificateViewComposable
import com.suraj854.healthquiz.presentation.qr_code.viewmodel.ScoreBoardViewModel
import com.suraj854.healthquiz.presentation.quiz.viewmodel.QuizViewModel
import com.suraj854.healthquiz.presentation.theme.BGColor
import com.suraj854.healthquiz.presentation.theme.PrimaryColor

@Composable
fun QRCodeScreen(
    navHostController: NavHostController,
    viewModel: QuizViewModel = hiltViewModel(),
    scoreBoardViewModel: ScoreBoardViewModel= hiltViewModel(),
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val quizScoreProgress = remember {
        mutableStateOf(userPreferences.getQuizScore())
    }
    val qrCodeBitmap by remember {
        mutableStateOf(
            generateQRCodeBitmap(
                userPreferences.getCertificateUrl() ?: ""
            )
        )
    }
    val qrCodeImageBitmap: ImageBitmap? = qrCodeBitmap?.asImageBitmap()

    LaunchedEffect(key1 = true) {
        scoreBoardViewModel.updateScoreInUserRecord()
        viewModel.saveCertificateRecords(context)
    }

    Scaffold(
        containerColor = BGColor,
    ) { innerPadding ->
        Box(modifier = Modifier) {
            Image(
                painter = painterResource(id = R.drawable.splash_bg),
                contentScale = ContentScale.FillBounds,
                contentDescription = "splash_bg",
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 25.dp),
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
                            .scale(0.9f),
                        contentDescription = "Logo"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 10.dp)
            ) {


                Row(modifier = Modifier.padding(16.dp)) {

                    BrandHeader()
                }
                Row(modifier = Modifier.padding(16.dp)) {
                    Column {

                        Text(
                            text = "Great Job!",
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "You’ve earned a certificate",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CertificateViewComposable(
                        context = LocalContext.current,
                        userName = userPreferences.getFirstName() + " " + userPreferences.getLastName(),
                        date = "date"
                    )
                }
                LinearProgressIndicator(
                    progress = { quizScoreProgress.value ?: 0f },
                    strokeCap = StrokeCap.Round,
                    color = Color(0xff1D61E7),
                    trackColor = PrimaryColor.copy(alpha = 0.2f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .padding(horizontal = 24.dp),
                )


                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "You've scored ${
                            userPreferences.getQuizScore()?.times(100)?.toULong()
                        }% in the Quiz",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,

                        )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier

                            .size(120.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.7.dp
                        ), // Set the elevation here
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        if (qrCodeImageBitmap != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    bitmap = qrCodeImageBitmap,
                                    contentDescription = "QR Code",
                                    modifier = Modifier.size(100.dp)
                                )
                            }

                        } else {
                            Text("Error generating QR Code", color = Color.Red)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Scan the QR Code",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,

                        )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "To download your Certificate",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,

                        )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {

                            navHostController.navigate(
                                Screen.SignUpScreen.route
                            ) {
                                popUpTo(
                                    navHostController.currentBackStackEntry?.destination?.id ?: -1
                                ) {
                                    inclusive = true
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xff1D61E7)),
                    ) {
                        Text(
                            text = "Home",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,

                            )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp), contentAlignment = Alignment.Center
                ) {

                }


            }

        }
    }


}