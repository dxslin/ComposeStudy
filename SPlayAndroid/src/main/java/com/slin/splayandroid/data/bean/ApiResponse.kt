package com.slin.splayandroid.data.bean

data class ApiResponse<T>(
    val resultCode: Int = RESULT_SUCCESS,
    val errorMsg: String? = null,
    val data: T?,
//    @Expose(serialize = true, deserialize = true)
//    val throwable: Throwable? = null,
) {

    companion object {
        const val RESULT_SUCCESS = 0
//        const val RESULT_ERROR = -5555
//
//        fun <T> error(throwable: Throwable): ApiResponse<T> {
//            return ApiResponse(
//                RESULT_ERROR,
//                errorMsg = throwable.toString(),
//                data = null,
//                throwable = throwable
//            )
//        }

    }
}

fun <T> ApiResponse<T>.isSuccessful(): Boolean {
    return resultCode == ApiResponse.RESULT_SUCCESS
}

class ErrorCodeException(val code: Int, errorMsg: String?) :
    Exception("ErrorCode: $code.\r\n ErrorMsg: $errorMsg")

fun <T> ApiResponse<T>.getFailException(): ErrorCodeException {
    return ErrorCodeException(resultCode, errorMsg)
}
