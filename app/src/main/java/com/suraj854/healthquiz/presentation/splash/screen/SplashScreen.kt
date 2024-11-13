package com.suraj854.healthquiz.presentation.splash.screen

import android.os.Build.VERSION.SDK_INT
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.suraj854.healthquiz.R
import com.suraj854.healthquiz.navigation.Screen
import ir.kaaveh.sdpcompose.sdp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavHostController) {


 LaunchedEffect(key1 = Unit) {
        withContext(Dispatchers.Main) {
            delay(3000)
            navController.navigate(
                Screen.SignUpScreen.route
            ) {
                popUpTo(navController.currentBackStackEntry?.destination?.id ?: -1) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.app_background),
            contentScale = ContentScale.Fit,
            contentDescription = "splash_bg", modifier = Modifier.fillMaxSize()
        )

        /*Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Center)
                .height(612.dp)
                .width(612.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(357.dp),
                contentDescription = "Logo"
            )
        }*/

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(92.sdp)

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.sdp), horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ministry_01),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(220.sdp)
                            .width(92.sdp).scale(1.6f),

                        contentDescription = "Logo"
                    )
                    Spacer(modifier = Modifier.width(60.sdp))
                    VerticalDivider(color = Color.Gray,modifier = Modifier.padding(vertical = 2.sdp))
                    Spacer(modifier = Modifier.width(32.sdp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(102.sdp).scale(1.1f),

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
    /*val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        //crops the image to circle shape
        modifier = Modifier
            .fillMaxSize(
            )
            .scale(1.2f),

        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = R.drawable.logo_animated_2)
                .build(), imageLoader = imageLoader
        ),
        contentDescription = "Loading animation",
        contentScale = ContentScale.Fit,
    )*/
}