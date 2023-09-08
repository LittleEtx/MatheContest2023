package algo

import utils.Vector2
import utils.degree
import kotlin.math.tan

const val THRESHOLD = 1e-8
private fun Ship.findSolution(position: Vector2, dir: Vector2) : Double {
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

fun Ship.detectWidthAt(x: Double, y: Double) : Double {
    return findSolution(Vector2(x, y), direction.rotate(90.degree)) +
            findSolution(Vector2(x, y), direction.rotate((-90).degree))
}

fun Ship.detectWidthAt(position: Vector2) = detectWidthAt(position.x, position.y)