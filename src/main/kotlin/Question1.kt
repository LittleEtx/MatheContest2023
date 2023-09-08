import algo.Ship
import algo.getDetectWidthAt
import map.BasicMap
import utils.Vector2
import utils.degree

fun main() {
    val map = BasicMap(
        originDeep = 70.0,
        slopeDir = Vector2.NORTH,
        slopeAngle = 1.5.degree
    )
    val ship = Ship(map = map, detectAngle = 120.degree)
    val dir = Vector2.WEST
    for (dis in -800..800 step 200) {
        val deep = String.format("%.2f", map.deepAt(dis.toDouble(), 0.0))
        val width = String.format("%.2f", ship.getDetectWidthAt(dis.toDouble(), 0.0, dir))
        println("$deep: $width")
    }
}