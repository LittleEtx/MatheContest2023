package algo

import utils.*
import kotlin.math.tan

private const val DISTANCE_STEP = 1.0
private const val THRESHOLD = 1e-8
val Vector2.normalDir get() =
    if (angle < 90.degree) rotate(90.degree)
    else rotate((-90).degree)
private fun Ship.getExpectedDistanceFrom(position: Vector2, limit: ClosedRange<Double>, dir: Vector2): ClosedRange<Double> {
    val normalDir = dir.normalDir
    val baseWidth = getHalfDetectWidth(position, normalDir)
    object {
        val Double.x get() = position.x + this * normalDir.x
        val Double.y get() = position.y + this * normalDir.y
        val Double.rate: Double get() {
            val targetPos = Vector2(x, y)
            val width = getHalfDetectWidth(targetPos, normalDir.reverse())
            val widthRemain = getHalfDetectWidth(targetPos, normalDir)
            return (width + baseWidth - this) / (width + widthRemain)
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
        // smaller the distance, larger the overlap rate
        return findDistanceAtRate(limit.end)..findDistanceAtRate(limit.start)
    }
}


/**
 * @return null when cannot satisfy
 */
fun Ship.getExpectedDistance(baseLine: LineSeg, limit: ClosedRange<Double>): Double? {
    val dir = Vector2.fromAngle(baseLine.angle)
    var length = 0.0
    var range = getExpectedDistanceFrom(baseLine.start, limit, dir)
    length += DISTANCE_STEP
    while (length <= baseLine.length) {
        range = range.intersect(getExpectedDistanceFrom(baseLine.start + dir * length, limit, dir))
        if (range == INVALID_RANGE) return null
        length += DISTANCE_STEP
    }
    return range.end // use the largest possible distance
}

private const val EQUAL_THRESHOLD = 1e-6
fun Ship.getLines(
    fromX: Double, toX: Double,
    fromY: Double, toY: Double,
    angle: Double,
    limit: ClosedRange<Double> = 0.1..0.2
) : List<LineSeg>? {
    val isInArea = { point: Vector2 ->
        point.x in (fromX - EQUAL_THRESHOLD)..(toX + EQUAL_THRESHOLD)
                && point.y in (fromY - EQUAL_THRESHOLD)..(toY + EQUAL_THRESHOLD)
    }
    val boundaryLines = listOf(
        Line(Vector2(fromX, fromY), Vector2(fromX, toY)),
        Line(Vector2(fromX, toY), Vector2(toX, toY)),
        Line(Vector2(toX, toY), Vector2(toX, fromY)),
        Line(Vector2(toX, fromY), Vector2(fromX, fromY)),
    )

    val startPoint = if (angle < 90.degree) Vector2(toX, fromY) else Vector2(fromX, fromY)
    val endPoint = if (angle < 90.degree) Vector2(fromX, toY) else Vector2(toX, toY)
    val shiftDir = Vector2.fromAngle(angle).normalDir

    fun searchLines(start: Vector2, end: Vector2, shiftDir: Vector2): List<LineSeg>? {
        val startDis = map.deepAt(start) * tan(detectAngle / 2)

        var baseLine = Line(start, angle).shift(shiftDir * startDis)
        val lines = mutableListOf<LineSeg>()
        while (true) {
            val points = boundaryLines
                .map { it.intersect(baseLine) }
                .filter(isInArea)
            if (points.size != 2) break
            val lineSeg = LineSeg(points[0], points[1])
            lines += lineSeg

            val dis = getExpectedDistance(lineSeg, limit) ?: return null // cannot satisfy
            baseLine = baseLine.shift(shiftDir * dis)
        }
        return lines
    }
    return searchLines(startPoint, endPoint, shiftDir)
}