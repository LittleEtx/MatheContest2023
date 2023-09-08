package q3
import algo.getLines
import utils.*

val ANGLE_STEP = 1.degree
val FROM_ANGLE = 0.degree
val TO_ANGLE = 360.degree

fun main() {

    data class Result(val angle: Double, val lines: List<LineSeg>, val length: Double)
    val results = mutableListOf<Result>()
    (FROM_ANGLE..TO_ANGLE step ANGLE_STEP).forEach { angle ->
        println()
        print("angle: ${angle.toDegree()}")
        val lines = searchArea.getLines(
            detectAngle = detectAngle,
            shipAngle = angle,
            limit = 0.1..0.2,
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