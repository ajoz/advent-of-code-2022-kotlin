package io.github.ajoz.utils

/**
 * Checks whether the given [IntRange] contains fully the other specified [IntRange].
 */
fun IntRange.containsAll(other: IntRange): Boolean =
    this.contains(other.first) && this.contains(other.last)

/**
 * Checks whether the given [IntRange] overlaps at all with the other specified [IntRange].
 */
fun IntRange.containsAny(other:IntRange): Boolean =
    this.contains(other.first) || this.contains(other.last)