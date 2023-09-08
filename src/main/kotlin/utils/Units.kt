package utils

inline val Double.degree get() = this * Math.PI / 180
inline val Int.degree get() = this.toDouble().degree
fun Double.toDegree() = this * 180 / Math.PI

inline val Double.nm get() = this * 1852
inline val Int.nm get() = this.toDouble().nm