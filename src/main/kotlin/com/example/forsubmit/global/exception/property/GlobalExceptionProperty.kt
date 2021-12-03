package com.example.forsubmit.global.exception.property

enum class GlobalExceptionErrorCode(
    override val errorMessage: String,
    override val status: Int,
    override val koreanMessage: String
) : ExceptionProperty {
    UNEXPECTED("Unexpected Exception Occurred", 500, "예기치 않은 오류가 발생했습니다."),
    INVALID_METHOD_ARGUMENT("Invalid Method Argument", 400, "Request Body가 이상합니다."),
    REQUEST_NOT_FOUND("Cannot Find Valid Controlelr", 404, "적절한 컨트롤러를 찾지 못했습니다.")
}
