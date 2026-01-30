package io.github.rain991.extensions

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Transforms all elements of the iterable concurrently.
 *
 * For each element, launches a coroutine to apply the [transform] function.
 *
 * Example:
 * ```
 * val userIds = listOf(1, 2, 3)
 *
 * val users = userIds.mapAsync { id ->
 *     api.getUser(id) // asynchronous call
 * }
 *
 * println(users)
 * ```
 *
 * Note:
 * - One coroutine is launched per element, avoid large collections.
 * - If any transform fails, all other coroutines will be cancelled.
 */
suspend fun <T, R> Iterable<T>.mapAsync(transform: suspend (T) -> R): List<R> = coroutineScope {
    map {
        async { transform(it) }
    }.awaitAll()
}