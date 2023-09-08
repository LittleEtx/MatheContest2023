import algo.Ship
import algo.getLines
import map.BasicMap
import utils.*

val ANGLE_STEP = 0.15.degree
val FROM_ANGLE = 87.degree
val TO_ANGLE = 93.degree

fun main() {
    val map = BasicMap(
        originDeep = 110.0,
        slopeDir = Vector2.EAST,
        slopeAngle = 1.5.degree,
    )
    val ship = Ship(map = map, detectAngle = 120.degree)

    data class Result(val angle: Double, val lines: List<LineSeg>, val length: Double)
    val results = mutableListOf<Result>()
    generateSequence(FROM_ANGLE) { it + ANGLE_STEP }.takeWhile { it < TO_ANGLE }.forEach { angle ->
        println()
        print("angle: ${angle.toDegree()}")
        val lines = ship.getLines(
            fromX = (-2).nm,
            fromY = (-1).nm,
            toX = 2.nm,
            toY = 1.nm,
            angle = angle,
            limit = 0.1..0.2
        ) ?: return@forEach
        val totalLength = lines.sumOf { it.length }
        print(" find solution of length $totalLength")
        results.add(Result(angle, lines, totalLength))
    }
    println()
    val best = results.minBy { it.length }


    println("Best angle: ${best.angle.toDegree()}")
    println("Total length: ${best.length}")
    println("Boundary lines:")
    best.lines.forEach { println("from:(${it.start.x}, ${it.start.y}) to:(${it.end.x}, ${it.end.y})") }
}