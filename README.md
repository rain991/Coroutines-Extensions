# Kotlin Coroutines Extensions

Library that extends standard library of Kotlin's coroutines with missing functions.

[Detailed documentation for all functions](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/index.html)

## Add to your project
Ensure you have MavenCentral in your `settings.gradle.kts`:
```
repositories {
        ...
        mavenCentral()
}
```
Add dependency in build.gradle.kts in project/subproject where helper functions are needed:
```
implementation("io.github.rain991:coroutines-extensions:1.0.0")
```


## Functions overview

| Name | Summary |
|---|---|
| [mapAsync](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/map-async.html) | `suspend fun <T, R> Iterable<T>.mapAsync(transform: suspend (T) -> R): List<R>`<br><br>Transforms all elements of the iterable concurrently. |
| [pairwise](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/pairwise.html) | `fun <T> Flow<T>.pairwise(): Flow<Pair<T, T>>`<br><br>For each emission after the first, emits a `Pair(previous, current)`. |
| [raceWith](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/race-with.html) | `suspend fun <T> Flow<T>.raceWith(other: Flow<T>): T`<br><br>Returns the value from the flow that emits first. |
| [retryWithExponentialBackoff](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/retry-with-exponential-backoff.html) | `fun <T> Flow<T>.retryWithExponentialBackoff(maxAttempts: Int, initialDelay: Duration, multiplier: Double = 2.0, retryOn: (Throwable) -> Boolean = { true }): Flow<T>`<br><br>Retries the flow when it fails, increasing the delay between retries exponentially. |
| [timeoutOrNull](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/timeout-or-null.html) | `fun <T> Flow<T>.timeoutOrNull(timeout: Duration): Flow<T?>`<br><br>Emits the first value from the upstream flow or `null` if no value is emitted within the given timeout. |
| [withDefault](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/with-default.html) | `fun <T> Flow<T>.withDefault(default: T): Flow<T>`<br><br>Emits the provided `default` value if the upstream flow is empty.<br><br>`fun <T> Flow<T>.withDefault(default: T, shouldDefault: (Throwable) -> Boolean): Flow<T>`<br><br>Emits the provided `default` value if the upstream flow is empty or if `shouldDefault` returns `true` for an exception. |
| [withFallback](https://rain991.github.io/Coroutines-Extensions/-coroutines%20-extensions/io.github.rain991.extensions/with-fallback.html) | `fun <T> Flow<T>.withFallback(fallbackWhen: (Throwable) -> Boolean = { true }, fallback: () -> Flow<T>): Flow<T>`<br><br>Emits from the upstream flow and switches to the fallback flow in case of an exception. |
