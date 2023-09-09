package q3

import algo.getLines
import utils.degree
import utils.toDegree

fun main() {
    val angle = (-90).degree
    print("angle: ${angle.toDegree()}")
    val lines = searchArea.getLines(
        detectAngle = detectAngle,
        shipAngle = angle,
        limit = 0.1..0.2,
    )!!
    val totalLength = lines.sumOf { it.length }
    print(" find solution of length $totalLength")
    println()
//    println("lines (size = ${lines.size}):")
//    lines.forEach { println("from:(${it.start.x}, ${it.start.y}) to:(${it.end.x}, ${it.end.y})") }
    lines.forEach { println("${it.start.x}") }
}