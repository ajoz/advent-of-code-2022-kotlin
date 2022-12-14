package io.github.ajoz.puzzles

import io.github.ajoz.utils.head
import io.github.ajoz.utils.readInput
import io.github.ajoz.utils.tail

/**
 * --- Day 1: Calorie Counting ---
 *
 * The jungle must be too overgrown and difficult to navigate in vehicles or access from the air; the Elves' expedition
 * traditionally goes on foot. As your boats approach land, the Elves begin taking inventory of their supplies. One
 * important consideration is food - in particular, the number of Calories each Elf is carrying (your puzzle input).
 *
 * The Elves take turns writing down the number of Calories contained by the various meals, snacks, rations, etc. that
 * they've brought with them, one item per line. Each Elf separates their own inventory from the previous Elf's
 * inventory (if any) by a blank line.
 *
 * For example, suppose the Elves finish writing their items' Calories and end up with the list in the Day01_test.txt file.
 *
 * This list represents the Calories of the food carried by five Elves:
 *
 *     The first Elf is carrying food with 1000, 2000, and 3000 Calories, a total of 6000 Calories.
 *     The second Elf is carrying one food item with 4000 Calories.
 *     The third Elf is carrying food with 5000 and 6000 Calories, a total of 11000 Calories.
 *     The fourth Elf is carrying food with 7000, 8000, and 9000 Calories, a total of 24000 Calories.
 *     The fifth Elf is carrying one food item with 10000 Calories.
 *
 * In case the Elves get hungry and need extra snacks, they need to know which Elf to ask: they'd like to know how
 * many Calories are being carried by the Elf carrying the most Calories. In the example above, this is 24000 (carried
 * by the fourth Elf).
 */
fun main() {
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

/**
 * --- Part One ---
 *
 * Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?
 */
private fun part1(input: List<String>): Int {
    return input.toCalories().maxByOrNull { it }?.value!!
}

/**
 * --- Part Two ---
 *
 * By the time you calculate the answer to the Elves' question, they've already realized that the Elf carrying the most
 * Calories of food might eventually run out of snacks.
 *
 * To avoid this unacceptable situation, the Elves would instead like to know the total Calories carried by the top
 * three Elves carrying the most Calories. That way, even if one of those Elves runs out of snacks, they still have
 * two backups.
 *
 * In the example above, the top three Elves are the fourth Elf (with 24000 Calories), then the third Elf
 * (with 11000 Calories), then the fifth Elf (with 10000 Calories). The sum of the Calories carried by these three elves
 * is 45000.
 *
 * Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?
 */
private fun part2(input: List<String>): Int {
    return input.toCalories().sortedDescending().take(3).sumOf { it.value }
}

@JvmInline
value class Calories(val value: Int) : Comparable<Calories> {
    override fun compareTo(other: Calories): Int =
        value.compareTo(other.value)
}

operator fun Calories.plus(other: Calories): Calories =
    Calories(this.value + other.value)

/**
 * Changes the List of input Strings to a List of Calories.
 */
fun List<String>.toCalories(): List<Calories> {
    // we create a single element list with Calories set to 0
    // we then go through the input and sum the Calories in the input lines
    // if an empty string is found then a new Calories 0 is added at the top of the list
    val initial = listOf(Calories(0))
    return fold(initial) { calories, inputLine ->
        val currentCalories = calories.head
        if (inputLine.isNotBlank()) {
            val inputCalories = Calories(inputLine.toInt())
            val newCalories = currentCalories + inputCalories
            listOf(newCalories) + calories.tail
        } else {
            initial + calories
        }
    }
}

