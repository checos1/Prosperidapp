package com.example.apphogares.backEnd.Services.ServicesSystem

import AzureStorageService
import android.annotation.SuppressLint
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.PQRs.TipoPeticionPQR
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class FileManagement {

    fun uploadFile(fileUri: Uri, idPeticionPQR: String, blobName: String, context: Context) {
        println("FileManagement Uploading file")
        val requestBody = createRequestBodyFromUri(context, fileUri)
        println("FileManagement RequestBody created")
        val containerName = "${Constants.containerName}/${idPeticionPQR}"
        uploadFileToAzure(containerName, blobName, requestBody!!, Constants.ACCESS_TOKEN1)
        println("FileManagement RequestBody created")

    }

    fun createRequestBodyFromUri(context: Context, fileUri: Uri): RequestBody? {
        return try {
            println("FileManagement createRequestBodyFromUri fileUri: ${fileUri}")
            // Get InputStream from the Uri
            val inputStream = context.contentResolver.openInputStream(fileUri)
            println("FileManagement createRequestBodyFromUri inputStream: $inputStream")
            // Create a temporary file to copy the content of the URI
            val tempFile = File.createTempFile("upload", "tmp", context.cacheDir).apply {
                outputStream().use { fileOut ->
                    inputStream?.copyTo(fileOut)
                }
            }
            println("FileManagement createRequestBodyFromUri tempFile: $tempFile")
            // Create RequestBody from the temporary file
            tempFile.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        } catch (e: Exception) {
            println("FileManagement createRequestBodyFromUri ${e.message}")
            e.printStackTrace()
            null
        }
    }

    fun uploadFileToAzure(containerName: String, blobName: String, fileRequestBody: RequestBody, sasToken: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseURLStorage)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AzureStorageService::class.java)
        val fullPath = "${containerName}/${blobName}?${sasToken}"

        val urlWithSASToken = "${Constants.baseURLStorage}${fullPath}"
        val call = service.uploadBlob(
            urlWithSASToken,
            fileRequestBody
        )

        println("FileManagement uploadFileToAzure call: ${call.request()}")
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("FileManagement Upload successful")
                } else {
                    println("FileManagement Upload failed message: ${response.message()} ")
                    println("FileManagement Upload failed errorBody: ${response.errorBody()?.string()} ")
                    println("FileManagement Upload failed code: ${response.code()}")
                    println("FileManagement Upload failed response: ${Gson().toJson(response.errorBody()?.string())}")
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    @SuppressLint("Range")
    fun getFileName(uri: Uri, context: Context): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                if (cut != null) {
                    result = result?.substring(cut + 1)
                }
            }
        }
        return result
    }
}




