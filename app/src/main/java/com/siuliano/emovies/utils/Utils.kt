package com.siuliano.emovies.utils

import com.siuliano.emovies.model.configuration.Configuration
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"

    private fun getTomorrowDate(): Date {
        return Calendar.getInstance(Locale.getDefault()).apply {
            add(Calendar.DAY_OF_YEAR, 1)
        }.time
    }

    fun getTomorrowDate(format: String = DEFAULT_DATE_FORMAT): String =
        SimpleDateFormat(format, Locale.getDefault()).format(getTomorrowDate())

}

object StringUtils {
    fun getThumbnailUrl(path: String) = getImageUrl(Configuration.smallSize, path)
    fun getPosterUrl(path: String) = getImageUrl(Configuration.largeSize, path)
    private fun getImageUrl(size: String, path: String) = "${Configuration.secureBaseUrl}/$size/$path"
}