package map

import assertEquals
import org.junit.jupiter.api.Test
import utils.Vector2
import utils.degree
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

class BasicMapTest {

    @Test
    fun `slope with angle 0`() {
        val map = BasicMap(0.0, Vector2(1.0, 0.0), 0.0)
        assertEquals(0.0, map.deepAt(0.0, 0.0))
        assertEquals(0.0, map.deepAt(1.0, 0.0))
        assertEquals(0.0, map.deepAt(2.0, -1.0))
    }

    @Test
    fun `slope with basic direction`() {
        val map = BasicMap(0.0, Vector2(1.0, 0.0), 45.degree)
        assertEquals(0.0, map.deepAt(0.0, 0.0))
        assertEquals(-1.0, map.deepAt(1.0, 0.0))
        assertEquals(-2.0, map.deepAt(2.0, -1.0))
        assertEquals(2.0, map.deepAt(-2.0, 3.0))
    }

    @Test
    fun `slope with direction of 30 degree`() {
        val slopeAngle = atan(1.0 / 2.0)
        val dir = 30.degree
        val map = BasicMap(2.0, Vector2.fromAngle(dir), slopeAngle)
        assertEquals(2.0, map.deepAt(0.0, 0.0))
        assertEquals(0.0, map.deepAt(4 * cos(dir), 4 * sin(dir)))
        assertEquals(0.0, map.deepAt(4 / cos(dir), 0.0))
    }
}
