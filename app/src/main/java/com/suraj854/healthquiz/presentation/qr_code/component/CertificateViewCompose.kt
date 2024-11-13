package com.suraj854.healthquiz.presentation.qr_code.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.suraj854.healthquiz.presentation.generate_certificate.component.CertificateView
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CertificateViewComposable(
    context: Context,
    userName: String = "",
    date: String = ""
) {
    val certificateView = CertificateView(context, userName = userName, date = date)
    Box(
        modifier = Modifier
            .fillMaxWidth().height(160.sdp),

        ) {
        Image(
            bitmap = certificateView.getCertificateBitmap().asImageBitmap(),
            contentDescription = "Certficate",
            modifier = Modifier.align(alignment = Alignment.Center)
        )

    }

}
