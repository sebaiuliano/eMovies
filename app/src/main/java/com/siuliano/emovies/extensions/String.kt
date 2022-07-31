package com.siuliano.emovies.extensions

fun String.extractNumbersToInt() = this.filter { character -> character.isDigit() }.toIntOrNull() ?: 0