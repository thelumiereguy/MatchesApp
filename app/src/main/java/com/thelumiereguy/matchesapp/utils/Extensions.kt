package com.thelumiereguy.matchesapp.utils


fun Any.getClassTag(): String = this.javaClass.simpleName

fun Any.getMethodTag(): String =
    getClassTag() + object : Any() {}.javaClass.enclosingMethod?.name




