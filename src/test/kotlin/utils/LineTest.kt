package utils

import assertEquals
import org.junit.jupiter.api.Test

class LineTest {
    @Test
    fun `line with angle 0`() {
        val line = Line(0.0, 1.0, 0.0)
        assertEquals(0.0, line.yAt(0.0))
        assertEquals(0.0, line.yAt(1.0))
        assertEquals(0.0, line.yAt(2.0))
    }

    @Test
    fun `vertical line should return NaN for given x, infinite otherwise`() {
        val line = Line(1.0, 0.0, 0.0)
        assert(line.yAt(0.0).isNaN())
        assert(line.yAt(1.0).isInfinite())
        assert(line.yAt(-2.0).isInfinite())
    }

    @Test
    fun `two lines intersect`() {
        val line1 = Line(1.0, 1.0, 0.0)
        val line2 = Line(1.0, -1.0, 0.0)
        val intersection = line1.intersect(line2)
        assertEquals(0.0, intersection.x)
        assertEquals(0.0, intersection.y)

        line2.shift(Vector2(1.0, -1.0)).intersect(line1).apply {
            assertEquals(1.0, x)
            assertEquals(-1.0, y)
        }

        val line3 = Line(1.0, 1.0, 2.0)
        assert(line1.intersect(line3).let {
            it.x.isInfinite() && it.y.isInfinite()
        })
    }

    @Test
    fun `test line shift`() {
        val line = Line(1.0, 1.0, 0.0)
        line.shift(Vector2(1.0, -1.0)).apply {
            assertEquals(1.0, a / b)
            assertEquals(0.0, c)
        }

        line.shift(Vector2(0.0, 1.0)).apply {
            assertEquals(0.0, yAt(1.0))
            assertEquals(1.0, yAt(0.0))
        }
    }

}