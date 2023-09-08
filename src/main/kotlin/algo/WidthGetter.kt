package algo

import utils.Vector2
import utils.degree
import kotlin.math.tan

private const val THRESHOLD = 1e-8
fun Ship.getHalfDetectWidth(position: Vector2, dir: Vector2) : Double {
    object {
        val Double.x get() = position.x + this * dir.x
        val Double.y get() = position.y + this * dir.y
        val Double.detectDeep get() = this / tan(detectAngle / 2)
    }.apply {
        var l = 0.0
        var r = 1.0
        while (r.detectDeep < map.deepAt(r.x, r.y)) r *= 2
        while (r - l > THRESHOLD) {
            val mid = (l + r) / 2
            if (mid.detectDeep < map.deepAt(mid.x, mid.y)) l = mid
            else r = mid
        }
        return l
    }
}

fun Ship.getDetectWidthAt(x: Double, y: Double, dir: Vector2) : Double {
    return getHalfDetectWidth(Vector2(x, y), dir.rotate(90.degree)) +
            getHalfDetectWidth(Vector2(x, y), dir.rotate((-90).degree))
}

fun Ship.getDetectWidthAt(position: Vector2, dir: Vector2) = getDetectWidthAt(position.x, position.y, dir)