package algo

import map.SeaMap
import utils.Vector2
import utils.degree
import kotlin.math.tan

private const val THRESHOLD = 1e-6
fun SeaMap.getHalfDetectWidth(position: Vector2, dir: Vector2, detectAngle: Double) : Double {
    object {
        val Double.x get() = position.x + this * dir.x
        val Double.y get() = position.y + this * dir.y
        val Double.detectDeep get() = this / tan(detectAngle / 2)
    }.apply {
        var l = 0.0
        var r = 1.0
        while (r.detectDeep < deepAt(r.x, r.y)) r *= 2
        while (r - l > THRESHOLD) {
            val mid = (l + r) / 2
            if (mid.detectDeep < deepAt(mid.x, mid.y)) l = mid
            else r = mid
        }
        return l
    }
}

fun SeaMap.getDetectWidthAt(x: Double, y: Double, shipDir: Vector2, detectAngle: Double) : Double {
    return getHalfDetectWidth(Vector2(x, y), shipDir.rotate(90.degree), detectAngle) +
            getHalfDetectWidth(Vector2(x, y), shipDir.rotate((-90).degree), detectAngle)
}

fun SeaMap.getDetectWidthAt(position: Vector2, shipDir: Vector2, detectAngle: Double)
        = getDetectWidthAt(position.x, position.y, shipDir, detectAngle)