package utils

val ClosedRange<Double>.end get() = endInclusive
fun ClosedRange<Double>.intersect(other: ClosedRange<Double>): ClosedRange<Double> {
    val start = maxOf(this.start, other.start)
    val end = minOf(this.end, other.end)
    return start..end
}
