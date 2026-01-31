# Kotlin Coroutines Extensions

<!-- DOCS START -->
//[library](../../index.md)/[io.github.rain991.extensions](index.md)

# Package-level declarations

## Functions

| Name | Summary |
|---|---|
| [mapAsync](map-async.md) | [common]<br>suspend fun &lt;[T](map-async.md), [R](map-async.md)&gt; [Iterable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-async.md)&gt;.[mapAsync](map-async.md)(transform: suspend ([T](map-async.md)) -&gt; [R](map-async.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[R](map-async.md)&gt;<br>Transforms all elements of the iterable concurrently. |
| [pairwise](pairwise.md) | [common]<br>fun &lt;[T](pairwise.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[pairwise](pairwise.md)(): ERROR CLASS: Symbol not found for Flow&lt;ERROR CLASS: Symbol not found for Pair&lt;T, T&gt;&gt;<br>For each emission after the first, emits a Pair(previous, current). |
| [raceWith](race-with.md) | [common]<br>suspend fun &lt;[T](race-with.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[raceWith](race-with.md)(other: ERROR CLASS: Symbol not found for Flow&lt;T&gt;): [T](race-with.md)<br>Returns the value from flow that emits first. |
| [retryWithExponentialBackoff](retry-with-exponential-backoff.md) | [common]<br>fun &lt;[T](retry-with-exponential-backoff.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[retryWithExponentialBackoff](retry-with-exponential-backoff.md)(maxAttempts: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), initialDelay: ERROR CLASS: Symbol not found for Duration, multiplier: [Double](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-double/index.html) = 2.0, retryOn: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = { true }): ERROR CLASS: Symbol not found for Flow&lt;T&gt;<br>Retries the flow when it fails, while increasing the delay between retries. |
| [timeoutOrNull](timeout-or-null.md) | [common]<br>fun &lt;[T](timeout-or-null.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[timeoutOrNull](timeout-or-null.md)(timeout: ERROR CLASS: Symbol not found for Duration): ERROR CLASS: Symbol not found for Flow&lt;T?&gt;<br>Emits the first value from the upstream flow or `null` if no value is emitted within the given [timeout](timeout-or-null.md). |
| [withDefault](with-default.md) | [common]<br>fun &lt;[T](with-default.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[withDefault](with-default.md)(default: [T](with-default.md)): ERROR CLASS: Symbol not found for Flow&lt;T&gt;<br>Emits the provided [default](with-default.md) value if the upstream flow is empty.<br>[common]<br>fun &lt;[T](with-default.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[withDefault](with-default.md)(default: [T](with-default.md), shouldDefault: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): ERROR CLASS: Symbol not found for Flow&lt;T&gt;<br>Emits the provided [default](with-default.md) value if the upstream flow is empty and . |
| [withFallback](with-fallback.md) | [common]<br>fun &lt;[T](with-fallback.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[withFallback](with-fallback.md)(fallbackWhen: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = { true }, fallback: () -&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;): ERROR CLASS: Symbol not found for Flow&lt;T&gt;<br>Emits from upstream flow, switches to fallback flow in case of an exception. |//[library](../../index.md)/[io.github.rain991.extensions](index.md)/[mapAsync](map-async.md)

# mapAsync

[common]\
suspend fun &lt;[T](map-async.md), [R](map-async.md)&gt; [Iterable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-async.md)&gt;.[mapAsync](map-async.md)(transform: suspend ([T](map-async.md)) -&gt; [R](map-async.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[R](map-async.md)&gt;

Transforms all elements of the iterable concurrently.

For each element, launches a coroutine to apply the [transform](map-async.md) function.

Example:

```kotlin
val userIds = listOf(1, 2, 3)

val users = userIds.mapAsync { id ->
    api.getUser(id) // asynchronous call
}

println(users)
```

Note:

- 
   One coroutine is launched per element, avoid large collections.
- 
   If any transform fails, all other coroutines will be cancelled.//[library](../../index.md)/[io.github.rain991.extensions](index.md)/[pairwise](pairwise.md)

# pairwise

[common]\
fun &lt;[T](pairwise.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[pairwise](pairwise.md)(): ERROR CLASS: Symbol not found for Flow&lt;ERROR CLASS: Symbol not found for Pair&lt;T, T&gt;&gt;

For each emission after the first, emits a Pair(previous, current).

Example:

```kotlin
flowOf(1, 2, 5, 10)
.pairwise()
.collect { (prev, current)->
 println("Received pair $prev to $current")
}
```

Result: (1,2) (2,5) (5, 10)//[library](../../index.md)/[io.github.rain991.extensions](index.md)/[raceWith](race-with.md)

# raceWith

[common]\
suspend fun &lt;[T](race-with.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[raceWith](race-with.md)(other: ERROR CLASS: Symbol not found for Flow&lt;T&gt;): [T](race-with.md)

Returns the value from flow that emits first.

Both flows are collected concurrently.

Only the first value is emitted.

If both flows have failed, returns the last exception.

Example:

```kotlin
val fast = flow {
    delay(100)
    emit("Fast")
}

val slow = flow {
    delay(1000)
    emit("Slow")
}

val result = raceWith(fast, slow)
println(result)
```

In this example, the function returns &quot;Fast&quot;, because the first flow emits earlier than the second one.//[library](../../index.md)/[io.github.rain991.extensions](index.md)/[retryWithExponentialBackoff](retry-with-exponential-backoff.md)

# retryWithExponentialBackoff

[common]\
fun &lt;[T](retry-with-exponential-backoff.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[retryWithExponentialBackoff](retry-with-exponential-backoff.md)(maxAttempts: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), initialDelay: ERROR CLASS: Symbol not found for Duration, multiplier: [Double](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-double/index.html) = 2.0, retryOn: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = { true }): ERROR CLASS: Symbol not found for Flow&lt;T&gt;

Retries the flow when it fails, while increasing the delay between retries.

The wait time grows exponentially: `initialDelay * multiplier^attempt`.

The flow stops retrying after [maxAttempts](retry-with-exponential-backoff.md) or if [retryOn](retry-with-exponential-backoff.md) returns false.

Example:

```kotlin
flow {
    emit(fetchData())
}
.retryWithExponentialBackoff(
    maxAttempts = 5,
    initialDelay = 100.milliseconds,
    multiplier = 2.0
)
.collect { data ->
    println("Got $data")
}
```

In this example, if `fetchData()` fails, it will retry up to 5 times, waiting 100ms, 200ms, 400ms, 800ms, and 1600ms between tries.//[library](../../index.md)/[io.github.rain991.extensions](index.md)/[timeoutOrNull](timeout-or-null.md)

# timeoutOrNull

[common]\
fun &lt;[T](timeout-or-null.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[timeoutOrNull](timeout-or-null.md)(timeout: ERROR CLASS: Symbol not found for Duration): ERROR CLASS: Symbol not found for Flow&lt;T?&gt;

Emits the first value from the upstream flow or `null` if no value is emitted within the given [timeout](timeout-or-null.md).

Timeout must be positive.

Example:

```kotlin
val result = flow {
    delay(2.seconds)
    emit("Hello")
}
.timeoutOrNull(1.seconds)
.first()

println(result)
```

Result will be `null`//[library](../../index.md)/[io.github.rain991.extensions](index.md)/[withDefault](with-default.md)

# withDefault

[common]\
fun &lt;[T](with-default.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[withDefault](with-default.md)(default: [T](with-default.md)): ERROR CLASS: Symbol not found for Flow&lt;T&gt;

Emits the provided [default](with-default.md) value if the upstream flow is empty.

Example:

```kotlin
val emptyFlow = emptyFlow<Int>()
emptyFlow.withDefault(0).collect { println(it) }
```

Result is 0.

[common]\
fun &lt;[T](with-default.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[withDefault](with-default.md)(default: [T](with-default.md), shouldDefault: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): ERROR CLASS: Symbol not found for Flow&lt;T&gt;

Emits the provided [default](with-default.md) value if the upstream flow is empty and .

CancellationException is always rethrown.

Example:

```kotlin
val failingFlow = flow {
    emit(1)
    throw ArithmeticException()
    emit(2)
}
failingFlow.withDefault(0) { it is ArithmeticException }.collect { println(it) }
```

Result is 1, 0

#### Parameters

common

| | |
|---|---|
| default | the value to emit if no value is emitted. |
| shouldDefault | returns [default](with-default.md) when true |//[library](../../index.md)/[io.github.rain991.extensions](index.md)/[withFallback](with-fallback.md)

# withFallback

[common]\
fun &lt;[T](with-fallback.md)&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;.[withFallback](with-fallback.md)(fallbackWhen: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = { true }, fallback: () -&gt; ERROR CLASS: Symbol not found for Flow&lt;T&gt;): ERROR CLASS: Symbol not found for Flow&lt;T&gt;

Emits from upstream flow, switches to fallback flow in case of an exception.

The fallback is triggered only if the exception matches `fallbackWhen` predicate. Example:

```kotlin
val resultFlow = apiFlow()
    .withFallback { cacheFlow() }

resultFlow.collect { value ->
    println(value)
}
```<!-- DOCS END -->
