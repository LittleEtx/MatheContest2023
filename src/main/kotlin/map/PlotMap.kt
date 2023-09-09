package map

import utils.Vector2
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * @param start starting point at most south-west
 * @param xDelta distance between two points in x direction
 * @param yDelta distance between two points in y direction
 * @param deeps deeps(i, j) is the deep of the point (start.x + j * xDelta, start.y + i * yDelta)
 */
class PlotMap(
    private val start: Vector2,
    private val xDelta: Double,
    private val yDelta: Double,
    private val deeps: List<List<Double>>,
): SeaMap {
    private val xSize = deeps[0].size
    private val ySize = deeps.size

    private fun locateIndex(position: Vector2): Pair<Int, Int> {
        val xIndex = floor((position.x - start.x) / xDelta).toInt()
        val yIndex = floor((position.y - start.y) / yDelta).toInt()
        return Pair(min(max(xIndex, 0), xSize - 2), min(max(yIndex, 0), ySize - 2))
    }

    override fun deepAt(position: Vector2): Double {
        val (xIndex, yIndex) = locateIndex(position)

        val (x, y) = position
        val x0 = start.x + xIndex * xDelta
        val y0 = start.y + yIndex * yDelta
        val deep00 = deeps[yIndex][xIndex]
        val deep01 = deeps[yIndex][xIndex + 1]
        val deep10 = deeps[yIndex + 1][xIndex]
        val deep11 = deeps[yIndex + 1][xIndex + 1]

        return deep00 + (deep01 - deep00) * (x - x0) / xDelta +
                (deep10 - deep00) * (y - y0) / yDelta +
                (deep11 + deep00 - deep01 - deep10) * (x - x0) * (y - y0) / (xDelta * yDelta)
    }
}