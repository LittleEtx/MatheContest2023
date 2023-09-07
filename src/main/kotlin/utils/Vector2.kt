package utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

data class Vector2(
    val x: Double,
    val y: Double,
) {
    companion object {
        fun fromAngle(angle: Double) = Vector2(cos(angle), sin(angle))
        val north = Vector2(1.0, 0.0)
        val west = Vector2(0.0, -1.0)
        val south = Vector2(-1.0, 0.0)
        val east = Vector2(0.0, 1.0)
    }

    val angle get() = atan2(y, x)
    fun rotate(angle: Double) = fromAngle(this.angle + angle)
    operator fun unaryMinus() = Vector2(-x, -y)
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
    operator fun times(other: Double) = Vector2(x * other, y * other)
    operator fun div(other: Double) = Vector2(x / other, y / other)
    operator fun times(other: Vector2) = x * other.x + y * other.y
}