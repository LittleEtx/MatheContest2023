import kotlin.math.absoluteValue

fun assertEquals(expected: Double, actual: Double) {
    assert((expected - actual).absoluteValue < 1e-6) {
        "expected: $expected, actual: $actual"
    }
}