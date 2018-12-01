package com.pgr301.dev.rest.exception


class UserInputValidationException(
        message: String,
        val httpCode : Int = 400
) : RuntimeException(message)