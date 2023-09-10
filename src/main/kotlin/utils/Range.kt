package utils

val ClosedRange<Double>.end get() = endInclusive
fun ClosedRange<Double>.intersect(other: ClosedRange<Double>): ClosedRange<Double> {
    val start = maxOf(this.start, other.start)
    val end = minOf(this.end, other.end)
    return start..end
}
infix fun ClosedRange<Double>.step(step: Double): Sequence<Double> =
    generateSequence(start) { it + step }.takeWhile { it <= endInclusive }
