package utils

infix fun ClosedRange<Double>.step(step: Double): Sequence<Double> =
    generateSequence(start) { it + step }.takeWhile { it <= endInclusive }
