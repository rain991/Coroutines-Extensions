package io.github.rain991.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

/**
 * Emits the provided [default] value if the upstream flow is empty.
 *
 * Example:
 * ```
 * val emptyFlow = emptyFlow<Int>()
 * emptyFlow.withDefault(0).collect { println(it) }
 * ```
 * Result is 0.
 */
fun <T> Flow<T>.withDefault(default: T): Flow<T> = flow {
    var emitted = false
    collect { value ->
        emitted = true
        emit(value)
    }
    if (!emitted) emit(default)
}

/**
 * Emits the provided [default] value if the upstream flow is empty and .
 *
 * [CancellationException] is always rethrown.
 *
 * Example:
 * ```
 * val failingFlow = flow {
 *     throw ArithmeticException()
 *     emit(2)
 * }
 * failingFlow.withDefault(50) { it is ArithmeticException }.collect { println(it) }
 * // Output: 50
 * ```
 *
 * @param default the value to emit if no value is emitted.
 * @param shouldDefault returns [default] when true
 */
fun <T> Flow<T>.withDefault(
    default: T,
    shouldDefault: (Throwable) -> Boolean
): Flow<T> = flow {
    var emitted = false
    try {
        collect { value ->
            emitted = true
            emit(value)
        }
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        if (shouldDefault(e)) emit(default)
        return@flow
    }
    if (!emitted) emit(default)
}
