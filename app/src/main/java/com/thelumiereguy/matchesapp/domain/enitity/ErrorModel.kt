package com.thelumiereguy.matchesapp.domain.enitity

import java.lang.Exception


/**
 * Data class to store the Exception,
 * The message of the exception and
 * the error code
 */
data class ErrorModel constructor(
    val exception: Exception,
    val message: String?,
    val errorCode: Int
)