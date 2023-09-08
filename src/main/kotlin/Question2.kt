import algo.getDetectWidthAt
import map.BasicMap
import utils.Vector2
import utils.degree
import utils.nm

fun main() {
    val map = BasicMap(
        originDeep = 120.0,
        slopeDir = Vector2.WEST,
        slopeAngle = 1.5.degree
    )
    val  detectAngle = 120.degree
    for(degree in 0..315 step 45) {
        var mile = 0.0
        val mileStep = 0.3
        val dir = Vector2.fromAngle(degree.degree)
        while (mile <= 2.1) {
            val width = map.getDetectWidthAt(dir * mile.nm, dir, detectAngle)
            print(String.format("%5.2f ", width))
            mile += mileStep
        }
        println()
    }
}