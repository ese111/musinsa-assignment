package com.example.musinsa.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.*

fun logger(msg: String) = Log.i("TAG", msg)

suspend fun getDrawable(context: Context, imageUrl: String): Drawable? {
    val ceh = CoroutineExceptionHandler { _, throwable ->
        logger("$throwable")
    }

    return CoroutineScope(SupervisorJob() + ceh).async {
        logger("코루틴 시작")
        val request = ImageRequest.Builder(context).data(imageUrl).build()
        ImageLoader(context).execute(request).drawable
    }.await()
}