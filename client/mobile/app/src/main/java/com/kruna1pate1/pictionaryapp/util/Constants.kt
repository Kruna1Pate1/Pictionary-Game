package com.kruna1pate1.pictionaryapp.util

import android.view.MotionEvent
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    val TAG: String = "Pictionary"

    val SERVER_IP: String = "192.168.43.139"

    //    val SERVER_IP: String = "192.168.43.194"
    val SERVER_PORT: Int = 6565

    val PLAYER_PREFERENCE_NAME = "player_preference"
    val PLAYER_PREFERENCE_KEY = "player"

    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        return sdf.format(Date())
    }
}