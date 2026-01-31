package io.github.rain991.extensions

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

/**
 * Runs two flows at the same time and returns the value from the one that emits first.
 *
 * Both flows are collected concurrently.
 * As soon as one flow emits a value, the other flow is cancelled.
 *
 * Example:
 * ```
 * val fast = flow {
 *     delay(100)
 *     emit("Fast")
 * }
 *
 * val slow = flow {
 *     delay(1000)
 *     emit("Slow")
 * }
 *
 * val result = raceWith(fast, slow)
 * println(result)
 * ```
 *
 * In this example, the function returns "Fast",
 * because the first flow emits earlier than the second one.
 */

suspend fun <T> Flow<T>.raceWith(other: Flow<T>): T = coroutineScope {
    val result = CompletableDeferred<T>()
    val failures = AtomicInteger(0)

    val job1 = launch {
        try {
            collect { value ->
                if (result.complete(value)) cancel()
            }
        } catch (e: Throwable) {
            if (failures.incrementAndGet() == 2 && !result.isCompleted) {
                result.completeExceptionally(e)
            }
        }
    }

    val job2 = launch {
        try {
            other.collect { value ->
                if (result.complete(value)) cancel()
            }
        } catch (e: Throwable) {
            if (failures.incrementAndGet() == 2 && !result.isCompleted) {
                result.completeExceptionally(e)
            }
        }
    }

    try {
        result.await()
    } finally {
        job1.cancel()
        job2.cancel()
    }
}