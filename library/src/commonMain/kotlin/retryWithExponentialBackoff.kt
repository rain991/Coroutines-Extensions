package io.github.rain991.extensions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import kotlin.math.pow
import kotlin.time.Duration

/**
 * Retries the flow when it fails, while increasing the delay between retries.
 *
 * The wait time grows exponentially: `initialDelay * multiplier^attempt`.
 *
 * The flow stops retrying after [maxAttempts] or if [retryOn] returns false.
 *
 * Example:
 * ```
 * flow {
 *     emit(fetchData())
 * }
 * .retryWithExponentialBackoff(
 *     maxAttempts = 5,
 *     initialDelay = 100.milliseconds,
 *     multiplier = 2.0
 * )
 * .collect { data ->
 *     println("Got $data")
 * }
 * ```
 *
 * In this example, if `fetchData()` fails, it will retry up to 5 times,
 * waiting 100ms, 200ms, 400ms, 800ms, and 1600ms between tries.
 */
fun <T> Flow<T>.retryWithExponentialBackoff(
    maxAttempts: Int,
    initialDelay: Duration,
    multiplier: Double = 2.0,
    retryOn: (Throwable) -> Boolean = { true }
): Flow<T> {
    require(maxAttempts > 0) { "Max attempts must be greater than 0" }
    require(multiplier >= 1) { "Multiplier must be greater or equal 1" }
    require(initialDelay.isPositive() || initialDelay == Duration.ZERO) {
        "Initial delay must be positive or 0"
    }

    return retryWhen { cause, attempt ->
        if (attempt >= maxAttempts || !retryOn(cause)) return@retryWhen false

        val delayTime = (initialDelay * multiplier.pow(attempt.toDouble()))

        delay(delayTime)
        true
    }
}