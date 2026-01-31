import app.cash.turbine.test
import io.github.rain991.extensions.withFallback
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WithFallbackTest {
    @Test
    fun `emits all values from primary flow when no exception occurred`() = runTest {
        val flow = flowOf(1, 2)
        val fallbackFlow = flowOf(4, 5)

        flow.withFallback {
            fallbackFlow
        }.test {
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `emits fallback values when exception occurred`() = runTest {
        val flow = flow {
            emit(1)
            throw RuntimeException()
            emit(2)
        }
        val fallbackFlow = flowOf(4, 5)

        flow.withFallback {
            fallbackFlow
        }.test {
            assertEquals(1, awaitItem())
            assertEquals(4, awaitItem())
            assertEquals(5, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `emits only pre-failure values when both failed`() = runTest {
        val flow = flow {
            emit(1)
            throw RuntimeException()
            emit(2)
        }
        val fallbackFlow = flow {
            emit(4)
            throw RuntimeException()
            emit(5)
        }

        flow.withFallback {
            fallbackFlow
        }.test {
            assertEquals(1, awaitItem())
            assertEquals(4, awaitItem())
            awaitError()
        }
    }

    @Test
    fun `does not breaks through cancellation exception`() = runTest {
        val flow = flow {
            emit(1)
            throw CancellationException()
            emit(2)
        }
        val fallbackFlow = flow {
            emit(4)
            emit(5)
        }

        flow.withFallback {
            fallbackFlow
        }.test {
            assertEquals(1, awaitItem())
            awaitError()
        }
    }
}