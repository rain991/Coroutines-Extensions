package io.github.rain991.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Emits pairs of values from the upstream flow.
 *
 * For each emission after the first, emits a Pair(previous, current).
 *
 * Example:
 * ```
 * flowOf(1, 2, 5, 10)
 * .pairwise()
 * .collect { (prev, current)->
 *  println("Received pair $prev to $current")
 * }
 * ```
 *
 * Result:
 * (1,2)
 * (2,5)
 * (5, 10)
 */
fun <T> Flow<T>.pairwise(): Flow<Pair<T, T>> = flow {
    var previous: T? = null
    var initialized = false

    collect { value ->

        if (initialized) emit(previous!! to value)
        previous = value
        initialized = true
    }
}