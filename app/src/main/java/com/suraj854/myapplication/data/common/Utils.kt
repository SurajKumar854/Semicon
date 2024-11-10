package com.suraj854.myapplication.data.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.util.Patterns

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.net.URL
import java.util.EnumMap
import javax.net.ssl.HttpsURLConnection

object Utils {

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun generateQRCodeBitmap(url: String, width: Int = 512, height: Int = 512): Bitmap? {
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
        hints[EncodeHintType.MARGIN] = 1 // Default margin

        val writer = QRCodeWriter()
        return try {
            val bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height, hints)
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            bmp
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    suspend fun isNetworkConnected(): Boolean {

        var isConnected = false
        try {
            val url =
                URL("https://clients3.google.com/generate_204").openConnection() as HttpsURLConnection
            url.setRequestProperty("User-Agent", "Android")
            url.setRequestProperty("Connection", "close")
            url.connectTimeout = 1000
            url.connect()

            if (url.responseCode == 204) isConnected = true

        } catch (e: Exception) {
            print(e)
            isConnected = false
        }

        return isConnected

    }
}

