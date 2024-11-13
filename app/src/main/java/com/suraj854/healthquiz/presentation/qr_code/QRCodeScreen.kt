package com.suraj854.healthquiz.presentation.qr_code

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.suraj854.healthquiz.presentation.theme.Blue
import com.suraj854.healthquiz.presentation.theme.DarkGray
import com.suraj854.healthquiz.presentation.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun QRCodeScreen(
    navHostController: NavHostController,
    viewModel: QuizViewModel = hiltViewModel(),
    scoreBoardViewModel: ScoreBoardViewModel = hiltViewModel(),
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
                painter = painterResource(id = R.drawable.app_background),
                contentScale = ContentScale.Fit,
                contentDescription = "splash_bg",
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)

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
                    Spacer(modifier = Modifier.height(2.sdp))
                  /*  Image(
                        painter = painterResource(id = R.drawable.ic_digital_india),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(52.sdp),
                        contentDescription = "Logo"
                    )*/
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.sdp)

                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.sdp), horizontalArrangement = Arrangement.Center
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
                                    .width(120.dp)
                                    .scale(1.1f),

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
                Row {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Text(
                                    text = "Great Job!",
                                    style = TextStyle(
                                        fontSize = 21.sp,
                                        color = Color(0xff0A0B68),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "You’ve earned a certificate",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color(0xff0A0B68),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CertificateViewComposable(
                                context = LocalContext.current,
                                userName = userPreferences.getFirstName() + " " + userPreferences.getLastName(),
                                date = "date"
                            )
                        }
                        Spacer(modifier = Modifier.height(8.sdp))
                        LinearProgressIndicator(
                            progress = { quizScoreProgress.value ?: 0f },
                            strokeCap = StrokeCap.Round,
                            color = Color(0xff1D61E7),
                            trackColor = Blue.copy(alpha = 0.2f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.sdp)
                                .padding(horizontal = 72.dp),
                        )


                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "You've scored ${
                                    userPreferences.getQuizScore()?.times(100)?.toULong()
                                }% in the Quiz",
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xff1C4BA7),

                                )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f),
                        verticalArrangement = Arrangement.Center
                    ) {


                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Scan the QR Code",
                                textAlign = TextAlign.Center,
                                fontSize = 21.sp,
                                color = Color(0xff0A0B68),
                                fontWeight = FontWeight.Bold

                                )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "to download your Certificate",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color(0xff0A0B68),
                                fontWeight = FontWeight.Bold

                                )
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier

                                    .size(120.sdp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(2),
                                elevation = CardDefaults.elevatedCardElevation(), // Set the elevation here
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
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.sdp)
                                        )
                                    }

                                } else {
                                    Text("Error generating QR Code", color = Color.Red)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))


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

                                border = BorderStroke(width = 1.dp,  color = Color(0xff0A0B68),),
                                shape = RoundedCornerShape(2),
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                            ) {
                                Text(
                                    text = "Home",
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xff0A0B68),


                                    )
                            }
                        }



                    }

                }







            }

        }
    }


}