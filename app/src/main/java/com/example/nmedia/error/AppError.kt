package com.example.nmedia.error

import android.database.SQLException
import com.example.nmedia.R
import okio.IOException
import java.lang.RuntimeException

sealed class AppError(var code: String): RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError = when (e) {
            is AppError -> e
            is SQLException -> DbError
            is IOException -> NetworkError
            else -> UnknownError
        }
    }
}

class ApiError(val status: Int, code: String): AppError(code)
object NetworkError : AppError("${R.string.network_error}")
object DbError : AppError("${R.string.error_db}")
object UnknownError: AppError("${R.string.error_unknown}")