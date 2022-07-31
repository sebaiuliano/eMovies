package com.siuliano.emovies.extensions

fun Double.toStringWithDecimals(decimalPlaces: Int = 2) = String.format("%.${decimalPlaces}f", this)
