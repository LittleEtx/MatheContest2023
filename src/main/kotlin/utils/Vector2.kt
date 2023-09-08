package utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vector2(
    val x: Double,
    val y: Double,
) {
    companion object {
        fun fromAngle(angle: Double) = Vector2(cos(angle), sin(angle))
        val NORTH = Vector2(1.0, 0.0)
        val WEST = Vector2(0.0, -1.0)
        val SOUTH = Vector2(-1.0, 0.0)
        val EAST = Vector2(0.0, 1.0)
    }

    val length get() = sqrt(x * x + y * y)
    val angle get() = atan2(y, x)
    fun rotate(angle: Double) = Vector2(
        x * cos(angle) - y * sin(angle),
        x * sin(angle) + y * cos(angle)
    )
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
    operator fun times(other: Double) = Vector2(x * other, y * other)
    operator fun div(other: Double) = Vector2(x / other, y / other)
    operator fun times(other: Vector2) = x * other.x + y * other.y
    operator fun unaryMinus() = this.reverse()
    fun reverse(): Vector2 = this * (-1.0)
}