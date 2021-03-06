package com.stephen.nb.test.compose.api

data class ApiResponse<T>(val resultCode: Int = RESULT_SUCCESS, val errorMsg: String? = null, val data: T) {
    companion object {
        const val RESULT_SUCCESS = 0
    }
}