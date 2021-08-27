package com.slin.splayandroid.data.bean

data class ApiResponse<T>(
    val resultCode: Int = RESULT_SUCCESS,
    val errorMsg: String? = null,
    val data: T
) {

    companion object {
        const val RESULT_SUCCESS = 0

    }
}

fun <T> ApiResponse<T>.isSuccessful(): Boolean {
    return resultCode == ApiResponse.RESULT_SUCCESS
}