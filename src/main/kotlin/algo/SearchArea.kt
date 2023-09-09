package algo

import map.SeaMap
import utils.Line
import utils.LineSeg
import utils.Vector2
import utils.degree
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

interface SearchArea {
    fun Vector2.isInArea(): Boolean
    fun Vector2.isNotInArea(): Boolean = !isInArea()
    val map: SeaMap

    /** Return the first touch point for the given direction */
    fun tangencyPoint(angle: Double): Vector2

    /** Return whether the line segment touch the boundary of the area */
    fun LineSeg.isIntersect(): Boolean
}

class RectArea(
    private val startX: Double,
    private val endX: Double,
    private val startY: Double,
    private val endY: Double,
    override val map: SeaMap,
) : SearchArea {

    val boundaryLines = listOf(
        Line(Vector2(startX, startY), Vector2(endX, startY)),
        Line(Vector2(endX, startY), Vector2(endX, endY)),
        Line(Vector2(endX, endY), Vector2(startX, endY)),
        Line(Vector2(startX, endY), Vector2(startX, startY)),
    )

    override fun Vector2.isInArea(): Boolean {
        return x in startX..endX && y in startY..endY
    }

    override fun tangencyPoint(angle: Double): Vector2 {
        // limit angle to [0, 360)
        val ang = angle - floor(angle / 360.degree) * 360.degree
        return when(ang) {
            in 0.degree..90.degree -> Vector2(startX, startY)
            in 90.degree..180.degree -> Vector2(endX, startY)
            in 180.degree..270.degree -> Vector2(endX, endY)
            in 270.degree..360.degree -> Vector2(startX,endY)
            else -> throw IllegalArgumentException("angle should be in [0, 360)")
        }
    }

    override fun LineSeg.isIntersect(): Boolean {
        return boundaryLines
            .map { it.intersect(line) }
            .filter { it.x.isFinite() && it.y.isFinite() }
            .any { min(start.x, end.x) <= it.x && it.x <= max(start.x, end.x) &&
                    min(start.y, end.y) <= it.y && it.y <= max(start.y, end.y) }

    }
}

