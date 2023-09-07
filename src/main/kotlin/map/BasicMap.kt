package map

import utils.Vector2
import kotlin.math.tan

class BasicMap(
    private val originHeight: Double,
    private val slopeDir: Vector2,
    private val slopeAngle: Double,
): SeaMap {
    override fun heightAt(at: Vector2): Double {
        val mapping = at * slopeDir
        return originHeight - mapping * tan(slopeAngle)
    }
}