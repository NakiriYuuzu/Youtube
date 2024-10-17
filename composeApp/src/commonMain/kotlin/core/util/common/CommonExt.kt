package core.util.common

import androidx.compose.ui.util.fastForEach
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
internal inline fun <T> List<T>.fastSumByFloat(selector: (T) -> Float): Float {
    contract { callsInPlace(selector) }
    var sum = 0f
    fastForEach { element ->
        sum += selector(element)
    }
    return sum
}


internal fun String.toDate(): String {
    return try {
        val instant = Instant.parse(this)
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val date = dateTime.date
        date.toString()
    } catch (e: Exception) {
        "N/A"
    }
}