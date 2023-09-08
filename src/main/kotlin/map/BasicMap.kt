package map

import utils.Vector2
import kotlin.math.tan

class BasicMap(
    private val originHeight: Double,
    private val slopeDir: Vector2,
    private val slopeAngle: Double,
): SeaMap {
    override fun deepAt(position: Vector2): Double {
        val mapping = position * slopeDir
        return originHeight - mapping * tan(slopeAngle)
    }
}