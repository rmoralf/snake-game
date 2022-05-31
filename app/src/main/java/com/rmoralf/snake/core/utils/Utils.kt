package com.rmoralf.snake.core.utils

import android.util.Log
import com.rmoralf.snake.core.utils.Constants.TAG

class Utils {
    companion object {
        fun printError(message: String?) {
            Log.d(TAG, message ?: "")
        }

        fun createSubroute(screen: String, arg: String): String {
            return "$screen/{$arg}"
        }

    }
}