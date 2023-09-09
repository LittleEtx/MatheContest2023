package algo

import map.SeaMap
import utils.*
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

    private val boundaryLines = listOf(
        LineSeg(Vector2(startX, startY), Vector2(endX, startY)),
        LineSeg(Vector2(endX, startY), Vector2(endX, endY)),
        LineSeg(Vector2(endX, endY), Vector2(startX, endY)),
        LineSeg(Vector2(startX, endY), Vector2(startX, startY)),
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
        return intersectWith(boundaryLines)
    }

}

private fun LineSeg.intersectWith(boundaryLines: List<LineSeg>) =
    boundaryLines.any { it intersectWith this }

/**
 * @param points should be in counter-clock-wise order
 */
class PolyArea(
    private val points: List<Vector2>,
    override val map: SeaMap,
): SearchArea {
    private val Int.next get() = (this + 1) % points.size
    private val Int.prev get() = (this - 1 + points.size) % points.size
    override fun Vector2.isInArea(): Boolean {
        points.forEachIndexed { index, point ->
            val nextPoint = points[index.next]
            if ((nextPoint - point) cross (this - point) < 0) return false
        }
        return true
    }

    private val Double.angleInRange get() = this - floor(this / 360.degree) * 360.degree

    private val ranges = points.mapIndexed { index, point ->
        val fromAngle = ((points[index.prev] - point).angle - 90.degree).angleInRange
        val toAngle = ((points[index.next] - point).angle + 90.degree).angleInRange
        if (fromAngle < toAngle) listOf(fromAngle..toAngle)
        else listOf(0.0.degree.. toAngle, fromAngle..360.degree)
    }

    override fun tangencyPoint(angle: Double): Vector2 {
        val ang = angle.angleInRange
        ranges.forEachIndexed { index, range ->
            if(range.any { ang in it }) return points[index]
        }
        throw Error("cannot find any matching points for angle $angle")
    }

    private val boundaryLines = points.mapIndexed { index, point ->
        LineSeg(point, points[index.next])
    }
    override fun LineSeg.isIntersect() = intersectWith(boundaryLines)
}

