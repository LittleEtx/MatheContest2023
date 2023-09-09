package algo

import map.SeaMap
import utils.*
import kotlin.math.tan

private const val DISTANCE_STEP = 0.1
private const val THRESHOLD = 1e-8

fun SeaMap.getExpectedDistanceFrom(
    position: Vector2,
    dir: Vector2,
    detectAngle: Double,
    overlapRate: Double,
): Double {
    val baseWidth = getHalfDetectWidth(position, dir, detectAngle)
    val baseWidthRemain = getHalfDetectWidth(position, dir.reverse(), detectAngle)
    object {
        val Double.x get() = position.x + this * dir.x
        val Double.y get() = position.y + this * dir.y
        val Double.rate: Double get() {
            val targetPos = Vector2(x, y)
            val width = getHalfDetectWidth(targetPos, dir.reverse(), detectAngle)
            return (width + baseWidth - this) / (baseWidth + baseWidthRemain)
        }
        fun findDistanceAtRate(limit: Double): Double {
            var l = 0.0
            var r = 1.0
            while (r.rate > limit) r *= 2
            while (r - l > THRESHOLD) {
                val mid = (l + r) / 2
                if (mid.rate > limit) l = mid
                else r = mid
            }
            return l
        }
    }.apply {
//       return findDistanceAtRate(overLeapRate)
        return (1 - overlapRate) * (baseWidth + baseWidthRemain)
    }
}

fun SeaMap.getExpectedDistanceFrom(
    position: Vector2,
    dir: Vector2,
    detectAngle: Double,
    limit: ClosedRange<Double>
): ClosedRange<Double> {
    // further the distance, smaller overlapping
    return getExpectedDistanceFrom(position, dir, detectAngle, limit.end)..
            getExpectedDistanceFrom(position, dir, detectAngle, limit.start)
}

/**
 * @return null when cannot satisfy
 */

fun SearchArea.getLines(
    detectAngle: Double,
    shipAngle: Double,
    limit: ClosedRange<Double> = 0.1..0.3,
) : List<LineSeg>? {
    val shipDir = Vector2.fromAngle(shipAngle)
    val shiftAngle = shipAngle + 90.degree
    val shiftDir = Vector2.fromAngle(shiftAngle)

    var curPoint = tangencyPoint(shiftAngle)
    val startDis = map.deepAt(curPoint) * tan(detectAngle / 2)
    curPoint += shiftDir * startDis

    object {
        fun getDetectedPoint(pos: Vector2, dir: Vector2, detectAngle: Double) =
            pos + dir * map.getHalfDetectWidth(pos, dir, detectAngle)

        fun getExpectedDistanceFrom(pos: Vector2) =
            map.getExpectedDistanceFrom(pos, shiftDir, detectAngle, limit)
        val Double.pos get() = curPoint + shipDir * this
        val Double.topDetectPt get() = getDetectedPoint(pos, shiftDir, detectAngle)
        val Double.botDetectPt get() = getDetectedPoint(pos, shiftDir.reverse(), detectAngle)

        fun touchArea(p1: Vector2, p2: Vector2) = p1.isInArea() || p2.isInArea() || LineSeg(p1, p2).isIntersect()
        fun Double.touchArea() = touchArea(topDetectPt, botDetectPt)

        fun Double.notTouchArea() = !touchArea()

    }.apply {
        val lines = mutableListOf<LineSeg>()
        // given init value to ensure touch area
        var l = -1000.0
        var r = 1000.0
        while (true) {
            // extend / shirk search area
            while (l.notTouchArea()) l += DISTANCE_STEP
            while (l.touchArea()) l -= DISTANCE_STEP

            while (r.notTouchArea()) r -= DISTANCE_STEP
            while (r.touchArea()) r += DISTANCE_STEP

            lines += LineSeg(l.pos, r.pos)
            var finished = true
            // get next line
            var range: ClosedRange<Double> = Double.NEGATIVE_INFINITY..Double.POSITIVE_INFINITY
            (l..r step DISTANCE_STEP)
                .filter { it.topDetectPt.isInArea() } // here we assume that the area is convex
                .forEach { dis ->
                    finished = false // has point at top
                    range = range.intersect(getExpectedDistanceFrom(dis.pos))
                    if (range.isEmpty()) return null // invalid
                }
            if (finished) break    // fill the area
            curPoint += shiftDir * range.end // shift max possible
        }
        return lines
    }

}
