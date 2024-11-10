package com.suraj854.healthquiz.presentation.generate_certificate.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.suraj854.healthquiz.R
import com.suraj854.healthquiz.data.common.UserPreferences
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CertificateView(
    context: Context,
    userName: String = "",
    date: String = "",

) : View(context) {

    // Paint object to define styles

    private val user_name_paint = Paint()
    private val userPreferences = UserPreferences(context)
    private val date_paint = Paint()
    private val userNameTypeFace: Typeface? = ResourcesCompat.getFont(
        context,
        R.font.user_name_font
    ) // Replace 'custom_font' with your font file name in res/font

    private val robotoTypeFace: Typeface? = ResourcesCompat.getFont(
        context,
        R.font.roboto
    ) // Replace 'custom_font' with your font file name in res/font


    // Paint object for drawing text


    // Bitmap object for the image
    private val certificateBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.certificate_new)

    private lateinit var certficateGenerated: Bitmap

    init {
        // Configure the Paint object


        user_name_paint.color = android.graphics.Color.BLACK
        user_name_paint.textSize = 49f
        user_name_paint.typeface = userNameTypeFace
        user_name_paint.isAntiAlias = true
        date_paint.color = android.graphics.Color.BLACK
        date_paint.textSize = 30f
        date_paint.typeface = robotoTypeFace
        date_paint.isAntiAlias = true


    }

    fun getCertificateBitmap(): Bitmap {

        // Create a bitmap with the same dimensions as the canvas
        val bitmap = Bitmap.createBitmap(1484, 1057, Bitmap.Config.ARGB_8888)
        // Create a canvas to draw onto the bitmap
        val canvas = Canvas(bitmap)

        // Scale the bitmap to the desired dimensions (842x595)
        val scaledBitmap = Bitmap.createScaledBitmap(certificateBitmap, 1484, 1057, true)

        // Draw the scaled bitmap onto the canvas
        canvas.drawBitmap(scaledBitmap, 0f, 0f, null)
        val scaleX = 1485f / 842f
        val scaleY = 1057f / 595f
        // Set the position for the text
        val x = 314f
        val y = 290f
        // New positions based on scaling
        val newX = x * scaleX
        val newY = y * scaleY

        // Draw the text "Suraj Kumar" on the canvas
        canvas.drawText(
            userPreferences.getFirstName() + " " + userPreferences.getLastName(),
            newX,
            newY,
            user_name_paint
        )
        // Draw the date
        canvas.drawText(getCurrentDateFormatted(), 628f * scaleX, 485f * scaleY, date_paint)

        // Return the bitmap


        return bitmap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Scale the bitmap to the desired dimensions (842x595)
        val scaledBitmap = Bitmap.createScaledBitmap(certificateBitmap, 1484, 1057, true)

        // Draw the scaled bitmap onto the canvas
        canvas.drawBitmap(scaledBitmap, 0f, 0f, null)
        val scaleX = 1485f / 842f
        val scaleY = 1057f / 595f
        // Set the position for the text
        val x = 314f
        val y = 290f
        // New positions based on scaling
        val newX = x * scaleX
        val newY = y * scaleY

        // Draw the text "Suraj Kumar" on the canvas
        canvas.drawText(
            userPreferences.getFirstName() + " " + userPreferences.getLastName(),
            newX,
            newY,
            user_name_paint
        )
        // Draw the date
        canvas.drawText(getCurrentDateFormatted(), 635f * scaleX, 485f * scaleY, date_paint)

    }

    fun getCurrentDateFormatted(): String {
        // Create a SimpleDateFormat object with the desired format
        val sdf = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())

        // Get the current date
        val currentDate = Date()

        // Format the current date
        return sdf.format(currentDate)
    }

    fun generatePdf(completed: (Boolean) -> (Unit)) {
        // Create a PdfDocument object000
        val pdfDocument = PdfDocument()

        // Set page info (page size: A4 595x842 pixels)
        val pageInfo = PdfDocument.PageInfo.Builder(1484, 1057, 1).create()

        // Start a new page
        val page = pdfDocument.startPage(pageInfo)

        // Draw the view's content on the PDF page's canvas
        draw(page.canvas)

        // Finish the page
        pdfDocument.finishPage(page)

        // Save the document to the external storage

        try {
            /* pdfDocument.writeTo(FileOutputStream(filePath))
             println("PDF generated at: ${filePath.absolutePath}")*/
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

            // Get the current date and time
            val currentDateTime = Date()
            val datetime = dateFormat.format(currentDateTime)
            val pdfName = "Certificate_$datetime.pdf"
            createAndWriteToCache(
                context = context,
                pdfName,
                pdfDocument
            ) { success, pdfUri ->
                if (success) {

                    uploadCertificateToFirebaseStorage(context, pdfName) { isSuccess ->
                        completed(true)

                    }
                } else {

                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Close the document
        pdfDocument.close()
    }

    fun createAndWriteToCache(
        context: Context,
        fileName: String,
        pdfDocument: PdfDocument,
        isSuccess: (Boolean, Uri) -> (Unit),
    ) {

        val cacheDir: File = context.cacheDir
        val myFile = File(cacheDir, fileName)

        try {

            pdfDocument.writeTo(FileOutputStream(myFile))
            isSuccess(true, Uri.fromFile(myFile))

            /*BufferedOutputStream(FileOutputStream(myFile)).use { bos ->
                bos.write(data.toByteArray())
                isSuccess(true)
            }*/
        } catch (e: IOException) {
            e.printStackTrace()
            isSuccess(false, Uri.fromFile(myFile))
        }
    }

    fun uploadCertificateToFirebaseStorage(
        context: Context,
        pdfName: String,
        isSuccess: (Boolean) -> (Unit),
    ) {
        val storage = Firebase.storage

        val cacheDir: File = context.cacheDir

        // Get the file from cache
        val pdfFile = File(cacheDir, pdfName)
        if (pdfFile.exists()) {
            val fileUri = Uri.fromFile(pdfFile)
            val storageRef: StorageReference =
                storage.reference.child("users/${userPreferences.getEmail()}/$pdfName")

            // Start the file upload
            val uploadTask = storageRef.putFile(fileUri)

            // Handle upload success or failure
            uploadTask.addOnSuccessListener {
                // File uploaded successfully
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Get the download URL of the uploaded file
                    val fileUrl = downloadUri.toString()
                    userPreferences.saveCertificateUrl(fileUrl)

                    isSuccess(true)
                    println("PDF uploaded successfully. URL: $fileUrl")
                }
            }.addOnFailureListener { exception ->
                // Handle unsuccessful uploads
                isSuccess(false)
                println("Upload failed: ${exception.message}")
            }
        }


    }
}