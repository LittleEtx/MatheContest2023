package algo

import map.SeaMap
import utils.Vector2
import utils.degree
import kotlin.math.floor

interface SearchArea {
    fun Vector2.isInArea(): Boolean
    fun Vector2.isNotInArea(): Boolean = !isInArea()
    val map: SeaMap

    /** Return the first touch point for the given direction */
    fun tangencyPoint(angle: Double): Vector2
}

class RectArea(
    private val beginX: Double,
    private val endX: Double,
    private val beginY: Double,
    private val endY: Double,
    override val map: SeaMap,
) : SearchArea {
    override fun Vector2.isInArea(): Boolean {
        return x in beginX..endX && y in beginY..endY
    }

    override fun tangencyPoint(angle: Double): Vector2 {
        // limit angle to [0, 360)
        val ang = angle - floor(angle / 360.degree) * 360.degree
        return when(ang) {
            in 0.degree..90.degree -> Vector2(beginX, beginY)
            in 90.degree..180.degree -> Vector2(endX, beginY)
            in 180.degree..270.degree -> Vector2(endX, endY)
            in 270.degree..360.degree -> Vector2(beginX,endY)
            else -> throw IllegalArgumentException("angle should be in [0, 360)")
        }
    }
}

