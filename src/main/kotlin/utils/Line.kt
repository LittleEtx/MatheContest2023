package utils

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

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
        val x = (b * other.c - c * other.b) / (a * other.b - b * other.a)
        val y = (c * other.a - a * other.c) / (a * other.b - b * other.a)
        return Vector2(x, y)
    }
    fun shift(displacement: Vector2): Line {
        val newC = c - a * displacement.x - b * displacement.y
        return Line(a, b, newC)
    }

    fun xAt(y: Double): Double = (-c - b * y) / a
    fun yAt(x: Double): Double = (-c - a * x) / b
}

fun Vector2.distanceTo(line: Line): Double {
    val a = line.a
    val b = line.b
    val c = line.c
    return abs(a * x + b * y + c) / sqrt(a * a + b * b)
}