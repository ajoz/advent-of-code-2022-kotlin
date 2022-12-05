package io.github.ajoz.utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> =
    File("src/main/resources/", "$name.txt")
        .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

fun String.halfs(): List<String> =
    listOf(
        substring(0, length/2),
        substring(length/2)
    )

fun String.firstRepeatingCharFrom(vararg other: String): String =
     get(indexOfFirst { tested -> other.fold(true) { acc, string -> string.contains(tested) && acc } }).toString()