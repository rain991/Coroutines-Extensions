package io.github.rain991.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlin.coroutines.cancellation.CancellationException

/**
 * Emits from upstream flow, switches to fallback flow in case of an exception.
 *
 * The fallback is triggered only if the exception matches `fallbackWhen` predicate.
 * Example:
 * ```
 * val resultFlow = apiFlow()
 *     .withFallback { cacheFlow() }
 *
 * resultFlow.collect { value ->
 *     println(value)
 * }
 * ```
 */
fun <T> Flow<T>.withFallback(fallbackWhen: (Throwable) -> Boolean = { true }, fallback: () -> Flow<T>): Flow<T> {
    return channelFlow {
        try {
            collect { value ->
                send(value)
            }
        } catch (e: Throwable) {
            if (e is CancellationException) throw e

            if (fallbackWhen(e)) {
                fallback().collect { value -> send(value) }
            } else throw e
        }
    }
}