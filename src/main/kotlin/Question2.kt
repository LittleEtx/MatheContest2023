import algo.Ship
import algo.detectWidthAt
import map.BasicMap
import utils.Vector2
import utils.degree
import utils.hm

fun main() {
    val map = BasicMap(
        originDeep = 120.0,
        slopeDir = Vector2.south,
        slopeAngle = 1.5.degree
    )
    val ship = Ship(map = map, detectAngle = 120.degree)
    for(degree in 0..315 step 45) {
        var mile = 0.0
        val mileStep = 0.3
        val dir = Vector2.fromAngle(degree.degree)
        ship.direction = dir
        while (mile <= 2.1) {
            val width = ship.detectWidthAt(dir * mile.hm)
            print(String.format("%5.2f ", width))
            mile += mileStep
        }
        println()
    }
}