package q3

import algo.RectArea
import map.BasicMap
import utils.Vector2
import utils.degree
import utils.nm

val map = BasicMap(
    originDeep = 110.0,
    slopeDir = Vector2.EAST,
    slopeAngle = 1.5.degree,
)
val detectAngle = 120.degree
val searchArea = RectArea(
    startX = (-2).nm,
    endX = 2.nm,
    startY = (-1).nm,
    endY = 1.nm,
    map = map,
)