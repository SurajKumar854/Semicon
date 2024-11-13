package com.suraj854.healthquiz.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.suraj854.healthquiz.R

@Composable
fun BrandHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Row {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f).padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mofw),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(68.dp)
                        .scale(1.3f),

                    contentDescription = "Logo"
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f).padding(horizontal = 16.dp), horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mohfw),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(68.dp),
                    contentDescription = "Logo"
                )
            }
        }


    }
}