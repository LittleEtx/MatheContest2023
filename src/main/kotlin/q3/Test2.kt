package q3

import algo.getDetectWidthAt
import utils.Vector2

fun main() {
    val pos = Vector2(-3345.4782073578917, -1853.0)
    map.getDetectWidthAt(
        position = pos,
        shipDir = Vector2.NORTH,
        detectAngle = detectAngle
    ).let(::println)

}