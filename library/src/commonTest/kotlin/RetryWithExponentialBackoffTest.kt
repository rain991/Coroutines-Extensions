import app.cash.turbine.test
import io.github.rain991.extensions.retryWithExponentialBackoff
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class RetryWithExponentialBackoffTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `retries with exponential backoff`() = runTest {
        var run = 0
        val flow = flow {
            delay(100)
            if (run == 0 || run == 1) throw RuntimeException().also { run++ }
            emit(2)
        }

        flow.retryWithExponentialBackoff(maxAttempts = 5, initialDelay = 1000.milliseconds, multiplier = 2.0).test {
            assertEquals(2, awaitItem())
            assertEquals(3300L, currentTime)
            awaitComplete()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `does not exceeds max retry count`() = runTest {
        var run = 0
        val flow = flow {
            delay(100)
            if (run == 0 || run == 1) throw RuntimeException().also { run++ }
            emit(2)
        }

        flow.retryWithExponentialBackoff(maxAttempts = 1, initialDelay = 1000.milliseconds, multiplier = 2.0).test {
            awaitError()
            assertEquals(1200L, currentTime)
        }
    }
}