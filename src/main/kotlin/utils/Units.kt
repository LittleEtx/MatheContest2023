package utils

inline val Double.degree get() = this * Math.PI / 180
inline val Int.degree get() = this.toDouble().degree

inline val Double.hm get() = this * 1852
inline val Int.hm get() = this * 1852