package com.example.hellokotlin.core.response

data class ErrorResponse(
    val message: String,
    val errorType: String = "Invalid Argument"
)