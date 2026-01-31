@file:Suppress("KotlinUnreachableCode")

import app.cash.turbine.test
import io.github.rain991.extensions.withDefault
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class WithDefaultTest {

    @Test
    fun `emits default value if flow is empty`() = runTest {
        val flow = emptyFlow<Int>()
        flow.withDefault(0).test {
            assertEquals(0, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `does not emit default value if flow is not empty`() = runTest {
        val flow = flowOf(1, 3)
        flow.withDefault(0).test {
            assertEquals(1, awaitItem())
            assertEquals(3, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `emits default value when shouldDefault is true`() = runTest {
        val flow = flow {
            emit(1)
            throw ArithmeticException()
            emit(2)
        }

        flow.withDefault(default = 0, shouldDefault = { it is ArithmeticException }).test {
            assertEquals(1, awaitItem())
            assertEquals(0, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `does not emit default value when shouldDefault is false`() = runTest {
        val flow = flow {
            emit(1)
            throw RuntimeException()
            emit(2)
        }

        flow.withDefault(default = 0, shouldDefault = { it is ArithmeticException }).test {
            assertEquals(1, awaitItem())
            awaitComplete()
        }
    }
}