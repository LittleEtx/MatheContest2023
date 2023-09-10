package q4

import algo.getLines
import utils.*

val ANGLE_STEP = 1.degree
val FROM_ANGLE = 0.degree
val TO_ANGLE = 180.degree
val limit = 0.0..0.2

val polynomial = listOf(
    Vector2((X_START_NM + 2.4).nm, (Y_START_NM + 0).nm),
    Vector2((X_START_NM + 4).nm, (Y_START_NM + 0).nm),
    Vector2((X_START_NM + 4).nm, (Y_START_NM + 4).nm),
)

fun main() {
    data class Result(val angle: Double, val lines: List<LineSeg>, val length: Double)
    val results = mutableListOf<Result>()
    println("Boundary lines: $polynomial")
    println("Searching with limit $limit")
    fun search(angle: Double) {
        print("angle: ${angle.toDegree()}")
        val lines = searchArea.getLines(
            detectAngle = detectAngle,
            shipAngle = angle,
            limit = limit,
        ) ?: return println()
        val totalLength = lines.sumOf { it.length }
        println(" find solution of length $totalLength")
        results.add(Result(angle, lines, totalLength))
    }

    (FROM_ANGLE..TO_ANGLE step ANGLE_STEP).forEach { search(it) }
    (FROM_ANGLE..TO_ANGLE step ANGLE_STEP).forEach { search(it + 180.degree) }
    println()
    if (results.isEmpty()) {
        println("No result!")
        return
    }

    val best = results.minBy { it.length }
    println("Best angle: ${best.angle.toDegree()}")
    println("Total length: ${best.length}")
    println("Survey lines:")
    best.lines.forEach { println("from:(${it.start.x}, ${it.start.y}) to:(${it.end.x}, ${it.end.y})") }
}