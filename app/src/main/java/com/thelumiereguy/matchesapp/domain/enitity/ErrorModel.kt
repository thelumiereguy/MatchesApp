package com.thelumiereguy.matchesapp.domain.enitity

import java.lang.Exception

data class ErrorModel constructor(
    val exception: Exception,
    val message:String?,
    val errorCode:Int)