import io.github.rain991.extensions.mapAsync
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MapAsyncTest {

    @Test
    fun `transforms all elements`() = runTest {
        val list = listOf(1, 2, 3, 10)

        val result = list.map { it * 2 }

        assertEquals(result, listOf(2, 4, 6, 20))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `preserves order of elements`() = runTest {
        val list = listOf(1, 2, 3, 10)

        val result = list.mapAsync {
            delay(100)
            if (it == 3) {
                delay(1000)
            }
            it * 2
        }
        assertEquals(listOf(2, 4, 6, 20), result)
        assertEquals(1100L, currentTime)
    }

    @Test
    fun `empty list results in empty list`() = runTest {
        val list = emptyList<Int>()

        val result = list.mapAsync { it * 2 }
        assertEquals(emptyList(), result)
    }
}