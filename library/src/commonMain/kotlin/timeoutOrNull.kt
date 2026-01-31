package io.github.rain991.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration

/**
 * Emits the first value from the upstream flow or `null`
 * if no value is emitted within the given [timeout].
 *
 * Timeout must be positive.
 *
 * Example:
 * ```
 * val result = flow {
 *     delay(2.seconds)
 *     emit("Hello")
 * }
 * .timeoutOrNull(1.seconds)
 * .first()
 *
 * println(result)
 * ```
 *
 * Result will be `null`
 */
fun <T> Flow<T>.timeoutOrNull(timeout: Duration): Flow<T?> = flow {
    require(timeout.isPositive()) { "Timeout must be positive" }
    val value = withTimeoutOrNull(timeout) {
        firstOrNull()
    }
    emit(value)
}