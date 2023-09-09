package q4

import algo.getLines
import utils.degree
import utils.toDegree

fun main() {
    val angle = 2.5.degree
    print("angle: ${angle.toDegree()}")
    val lines = searchArea.getLines(
        detectAngle = detectAngle,
        shipAngle = angle,
        limit = limit,
    ) ?: return
    val totalLength = lines.sumOf { it.length }
    print(" find solution of length $totalLength")
    println()
//    println("lines (size = ${lines.size}):")
//    lines.forEach { println("from:(${it.start.x}, ${it.start.y}) to:(${it.end.x}, ${it.end.y})") }
    lines.forEach { println("${it.start.x}") }
}