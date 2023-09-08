package algo

import map.SeaMap
import utils.Vector2

class Ship(
    val map: SeaMap,
    val detectAngle: Double,
) {
    var direction: Vector2 = Vector2(1.0, 0.0)
}
