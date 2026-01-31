import app.cash.turbine.test
import io.github.rain991.extensions.timeoutOrNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class TimeoutOrNullTest {
    @Test
    fun `returns null when flow timeouts`() = runTest {
        val flow = flow {
            delay(2000)
            emit(4)
        }

        flow.timeoutOrNull(1.seconds).test {
            assertEquals(null, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `returns result correctly`() = runTest {
        val flow = flow {
            delay(2000)
            emit(4)
        }

        flow.timeoutOrNull(5.seconds).test {
            assertEquals(4, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `exceptions are thrown`() = runTest {
        val flow = flow {
            delay(2000)
            throw RuntimeException()
            emit(5)
        }

        flow.timeoutOrNull(5.seconds).test {
            awaitError()
        }
    }
}