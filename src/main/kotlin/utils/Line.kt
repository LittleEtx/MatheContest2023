package utils

import kotlin.math.cos
import kotlin.math.sin

data class LineSeg(
    val start: Vector2,
    val end: Vector2,
) {
    constructor(from: Vector2, angle: Double, length: Double) : this(
        from,
        from + Vector2.fromAngle(angle) * length
    )
    val angle = (end - start).angle
    val length = (end - start).length
    val line = Line(p1 = start, p2 = end)
}

data class Line(
    val a: Double,
    val b: Double,
    val c: Double,
) {
    constructor(point: Vector2, angle: Double) : this(
        a = sin(angle),
        b = -cos(angle),
        c = -sin(angle) * point.x + cos(angle) * point.y
    )
    constructor(p1: Vector2, p2: Vector2) : this(
        a = p2.y - p1.y,
        b = p1.x - p2.x,
        c = p2.x * p1.y - p1.x * p2.y
    )

    fun intersect(other: Line): Vector2 {
        val x = (c * other.b - b * other.c) / (a * other.b - b * other.a)
        val y = (a * other.c - c * other.a) / (a * other.b - b * other.a)
        return Vector2(x, y)
    }
    fun shift(displacement: Vector2): Line {
        val newC = c - a * displacement.x - b * displacement.y
        return Line(a, b, newC)
    }

    fun xAt(y: Double): Double = (-c - b * y) / a
    fun yAt(x: Double): Double = (-c - a * x) / b
}