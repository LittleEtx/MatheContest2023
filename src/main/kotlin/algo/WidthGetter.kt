package algo

import utils.Vector2
import utils.degree
import kotlin.math.tan

const val THRESHOLD = 1e-8
private fun Ship.findSolution(at: Vector2, dir: Vector2) : Double {
    object {
        val Double.x get() = at.x + this * dir.x
        val Double.y get() = at.y + this * dir.y
        val Double.detectHeight get() = this / tan(detectAngle / 2)
    }.apply {
        var l = 0.0
        var r = 1.0
        while (r.detectHeight < map.heightAt(r.x, r.y)) r *= 2
        while (r - l > THRESHOLD) {
            val mid = (l + r) / 2
            if (mid.detectHeight < map.heightAt(mid.x, mid.y)) l = mid
            else r = mid
        }
        return l
    }
}

fun Ship.detectWidthAt(x: Double, y: Double, shipDir: Vector2) : Double {
    return findSolution(Vector2(x, y), shipDir.rotate(90.degree)) +
            findSolution(Vector2(x, y), shipDir.rotate((-90).degree))
}

fun Ship.detectWidthAt(at: Vector2, shipDir: Vector2) = detectWidthAt(at.x, at.y, shipDir)