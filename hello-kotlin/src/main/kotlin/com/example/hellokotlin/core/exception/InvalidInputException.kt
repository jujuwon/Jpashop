package com.example.hellokotlin.core.exception

class InvalidInputException(
    message: String = "Invalid Input"
) : RuntimeException(message)