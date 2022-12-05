package io.github.ajoz.puzzles

import io.github.ajoz.utils.readInput
import java.lang.IllegalArgumentException

/**
 * --- Day 2: Rock Paper Scissors ---
 *
 * The Elves begin to set up camp on the beach. To decide whose tent gets to be closest to the snack storage, a giant
 * Rock Paper Scissors tournament is already in progress.
 *
 * Rock Paper Scissors is a game between two players. Each game contains many rounds; in each round, the players each
 * simultaneously choose one of Rock, Paper, or Scissors using a hand shape. Then, a winner for that round is selected:
 * Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock. If both players choose the same shape,
 * the round instead ends in a draw.
 *
 * Appreciative of your help yesterday, one Elf gives you an encrypted strategy guide (your puzzle input) that they say
 * will be sure to help you win. "The first column is what your opponent is going to play: A for Rock, B for Paper,
 * and C for Scissors. The second column--" Suddenly, the Elf is called away to help with someone's tent.
 *
 * The second column, you reason, must be what you should play in response: X for Rock, Y for Paper, and Z for Scissors.
 * Winning every time would be suspicious, so the responses must have been carefully chosen.
 *
 * The winner of the whole tournament is the player with the highest score. Your total score is the sum of your scores
 * for each round. The score for a single round is the score for the shape you selected (1 for Rock, 2 for Paper,
 * and 3 for Scissors) plus the score for the outcome of the round (0 if you lost, 3 if the round was a draw,
 * and 6 if you won).
 *
 * Since you can't be sure if the Elf is trying to help you or trick you, you should calculate the score you would get
 * if you were to follow the strategy guide.
 */
fun main() {
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 12)
    println(part2(input))
}

/**
 * --- Part One ---
 *
 * What would your total score be if everything goes exactly according to your strategy guide?
 */
private fun part1(input: List<String>): Int {
    return input.fold(0) { totalScore, inputLine ->
        val (opponentShape, playerShape) = inputLine.split(" ").map { it.toShape() }
        totalScore + playerShape.points + playerShape.getOutcomeFor(opponentShape).points
    }
}

/**
 * --- Part Two ---
 *
 * The Elf finishes helping with the tent and sneaks back over to you. "Anyway, the second column says how the round
 * needs to end: X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
 * Good luck!"
 *
 * The total score is still calculated in the same way, but now you need to figure out what shape to choose so the round
 * ends as indicated. The example above now goes like this:
 *
 *     In the first round, your opponent will choose Rock (A), and you need the round to end in a draw (Y), so you also
 *     choose Rock. This gives you a score of 1 + 3 = 4.
 *     In the second round, your opponent will choose Paper (B), and you choose Rock so you lose (X) with a score
 *     of 1 + 0 = 1.
 *     In the third round, you will defeat your opponent's Scissors with Rock for a score of 1 + 6 = 7.
 *
 * Now that you're correctly decrypting the ultra top secret strategy guide, you would get a total score of 12.
 */
private fun part2(input: List<String>): Int {
    return input.fold(0) { totalScore, inputLine ->
        val (opponent, game) = inputLine.split(" ")
        val opponentShape = opponent.toShape()
        val gameOutcome = game.toOutcome()
        val playerShape = opponentShape.getShapeFor(gameOutcome)

        totalScore + playerShape.points + gameOutcome.points
    }
}

enum class Shape(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
}

enum class Outcome(val points: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6),
}

fun Shape.getOutcomeFor(other: Shape): Outcome =
    when (this) {
        Shape.ROCK -> when (other) {
            Shape.ROCK -> Outcome.DRAW
            Shape.SCISSORS -> Outcome.WIN
            Shape.PAPER -> Outcome.LOSS
        }
        Shape.SCISSORS -> when (other) {
            Shape.ROCK -> Outcome.LOSS
            Shape.SCISSORS -> Outcome.DRAW
            Shape.PAPER -> Outcome.WIN
        }
        Shape.PAPER -> when (other) {
            Shape.ROCK -> Outcome.WIN
            Shape.SCISSORS -> Outcome.LOSS
            Shape.PAPER -> Outcome.DRAW
        }
    }

fun String.toShape(): Shape =
    when {
        this == "A" || this == "X" -> Shape.ROCK
        this == "B" || this == "Y" -> Shape.PAPER
        this == "C" || this == "Z" -> Shape.SCISSORS
        else -> throw IllegalArgumentException("Unknown shape type: >$this<")
    }

fun String.toOutcome(): Outcome =
    when {
        this == "X" -> Outcome.LOSS
        this == "Y" -> Outcome.DRAW
        this == "Z" -> Outcome.WIN
        else -> throw IllegalArgumentException("Unknown outcome type: >$this<")
    }

fun Shape.getShapeFor(outcome: Outcome): Shape =
    when (this) {
        Shape.ROCK -> when (outcome) {
            Outcome.WIN -> Shape.PAPER
            Outcome.DRAW -> Shape.ROCK
            Outcome.LOSS -> Shape.SCISSORS
        }
        Shape.PAPER -> when (outcome) {
            Outcome.WIN -> Shape.SCISSORS
            Outcome.DRAW -> Shape.PAPER
            Outcome.LOSS -> Shape.ROCK
        }
        Shape.SCISSORS -> when (outcome) {
            Outcome.WIN -> Shape.ROCK
            Outcome.DRAW -> Shape.SCISSORS
            Outcome.LOSS -> Shape.PAPER
        }
    }

