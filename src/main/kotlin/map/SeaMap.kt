package map

import utils.Vector2

interface SeaMap {
    fun deepAt(position: Vector2) : Double
    fun deepAt(x: Double, y: Double) : Double = deepAt(Vector2(x, y))
}