import algo.Ship
import algo.detectWidthAt
import map.BasicMap
import utils.Vector2
import utils.degree

fun main() {
    val map = BasicMap(
        originHeight = 70.0,
        slopeDir = Vector2.north,
        slopeAngle = 1.5.degree
    )
    val ship = Ship(map = map, detectAngle = 120.degree)
    val dir = Vector2.west
    for (dis in -800..800 step 200) {
        val height = String.format("%.2f", map.heightAt(dis.toDouble(), 0.0))
        val width = String.format("%.2f", ship.detectWidthAt(dis.toDouble(), 0.0, dir))
        println("$height: $width")
    }
}