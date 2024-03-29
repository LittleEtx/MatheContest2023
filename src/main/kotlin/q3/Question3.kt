package q3
import algo.getLines
import utils.*

val ANGLE_STEP = 0.1.degree
val FROM_ANGLE = 87.5.degree
val TO_ANGLE = 92.5.degree


fun main() {
    data class Result(val angle: Double, val lines: List<LineSeg>, val length: Double)
    val results = mutableListOf<Result>()
    fun search(angle: Double) {
        println()
        print("angle: ${angle.toDegree()}")
        val lines = searchArea.getLines(
            detectAngle = detectAngle,
            shipAngle = angle,
            limit = 0.1..0.2,
        ) ?: return
        val totalLength = lines.sumOf { it.length }
        print(" find solution of length $totalLength")
        results.add(Result(angle, lines, totalLength))
    }

    (FROM_ANGLE..TO_ANGLE step ANGLE_STEP).forEach { search(it) }
    (FROM_ANGLE..TO_ANGLE step ANGLE_STEP).forEach { search(it + 180.degree) }
    println()
    val best = results.minBy { it.length }


    println("Best angle: ${best.angle.toDegree()}")
    println("Total length: ${best.length}")
    println("Survey lines:")
    best.lines.forEach { println("from:(${it.start.x}, ${it.start.y}) to:(${it.end.x}, ${it.end.y})") }
}