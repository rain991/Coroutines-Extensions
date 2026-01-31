import app.cash.turbine.test
import io.github.rain991.extensions.pairwise
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PairwiseTest {

    // Verifies the example mentioned in pairwise KDOC.
    @Test
    fun `emits consecutive pairs`() = runTest {
        val flow = flowOf(1, 2, 5, 10)

        flow.pairwise().test {
            assertEquals(1 to 2, awaitItem())
            assertEquals(2 to 5, awaitItem())
            assertEquals(5 to 10, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `empty flow results in nothing emitted`() = runTest {
        emptyFlow<Int>().pairwise().test {
            awaitComplete()
        }
    }

    @Test
    fun `single element flow results in nothing emitted`() = runTest {
        flowOf(1).pairwise().test {
            awaitComplete()
        }
    }

    @Test
    fun `exceptions are propagated`() = runTest {
        val flowWithException = flow<Int> {
            emit(1)
            throw RuntimeException()
        }
        flowWithException.pairwise().test {
            awaitError()
        }
    }
}