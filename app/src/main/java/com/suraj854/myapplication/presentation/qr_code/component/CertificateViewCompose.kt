package com.suraj854.myapplication.presentation.qr_code.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.suraj854.myapplication.presentation.generate_certificate.component.CertificateView

@Composable
fun CertificateViewComposable(
    context: Context,
    userName: String = "",
    date: String = ""
) {
    val certificateView = CertificateView(context, userName = userName, date = date)
    Box(
        modifier = Modifier
            .fillMaxWidth(),

        ) {
        Image(
            bitmap = certificateView.getCertificateBitmap().asImageBitmap(),
            contentDescription = "Certficate"
        )

    }

}
