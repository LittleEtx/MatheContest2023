package map

import utils.Vector2

interface SeaMap {
    fun heightAt(at: Vector2) : Double
    fun heightAt(x: Double, y: Double) : Double = heightAt(Vector2(x, y))
}