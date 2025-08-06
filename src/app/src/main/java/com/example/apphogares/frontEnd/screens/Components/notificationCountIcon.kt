package com.example.apphogares.frontEnd.screens.Components

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.apphogares.MainActivity
import com.example.apphogares.R

@RequiresApi(Build.VERSION_CODES.N_MR1)
@Composable
fun notificationCountIcon(context: Context, notificationCount: Int ) {

    val shortcutManager = context.getSystemService(ShortcutManager::class.java)

    val shortcutInfo = remember { ShortcutInfo.Builder(context, "Prosperapp")
        .setShortLabel("Prosperapp")
        .setIcon(Icon.createWithBitmap(yourCustomIconWithCount(context, notificationCount)))
        .setIntent(Intent(Intent.ACTION_VIEW, null, context, MainActivity::class.java))
        .build()
    }

    shortcutManager.reportShortcutUsed("Prosperapp")
    shortcutManager.dynamicShortcuts = listOf(shortcutInfo)

}


fun yourCustomIconWithCount(context: Context, notificationCount: Int): Bitmap? {
    // Load your existing icon drawable
    val iconDrawable: Drawable? = ContextCompat.getDrawable(context, R.mipmap.ic_launcher)

    // Create a blank Bitmap with the same size as the icon
    val size = iconDrawable?.intrinsicWidth ?: 0
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

    // Create a Canvas to draw on the Bitmap
    val canvas = Canvas(bitmap)

    // Draw the existing icon onto the Bitmap
    iconDrawable?.bounds?.set(0, 0, size, size)
    iconDrawable?.draw(canvas)

    // Create a Paint object for text
    val textPaint = Paint()
    textPaint.isAntiAlias = true
    textPaint.color = Color.WHITE // You can set the text color
    textPaint.textSize = 36f // Adjust the text size as needed

    // Calculate the position to place the notification count (top-right corner)
    val text = notificationCount.toString()
    val textWidth = textPaint.measureText(text)
    val x = size - textWidth
    val y = textPaint.textSize

    // Draw the notification count text in the top-right corner of the icon
    canvas.drawText(text, x, y, textPaint)

    return bitmap
}

