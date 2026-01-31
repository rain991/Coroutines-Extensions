import io.github.rain991.extensions.raceWith
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("KotlinUnreachableCode")
class RaceWithTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fastest flow wins`() = runTest {
        val fastFlow = flow {
            delay(500)
            emit(3)
        }

        val slowFlow = flow {
            delay(1500)
            emit(6)
        }

        val result = fastFlow.raceWith(slowFlow)
        assertEquals(3, result)
        assertEquals(500L, currentTime)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fastest flow depends only on first emitted value`() = runTest {
        val fastFlow = flow {
            delay(500)
            emit(3)
            delay(10000)
            emit(5)
        }

        val slowFlow = flow {
            delay(1500)
            emit(6)
            delay(1500)
            emit(8)
        }

        val result = fastFlow.raceWith(slowFlow)
        assertEquals(3, result)
        assertEquals(500L, currentTime)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `returns result from slower flow, when faster one fails`() = runTest {
        val fastFlow = flow {
            delay(500)
            throw RuntimeException()
            emit(3)
        }

        val slowFlow = flow {
            delay(2000)
            emit(6)
        }

        val result = fastFlow.raceWith(slowFlow)
        assertEquals(6, result)
        assertEquals(2000L, currentTime)
    }

}