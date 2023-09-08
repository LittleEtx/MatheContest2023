package utils

val INVALID_RANGE = Double.NaN..Double.NaN
val ClosedRange<Double>.end get() = endInclusive
fun ClosedRange<Double>.intersect(other: ClosedRange<Double>): ClosedRange<Double> {
    val start = maxOf(this.start, other.start)
    val end = minOf(this.end, other.end)
    return if (start < end) start..end else INVALID_RANGE
}
