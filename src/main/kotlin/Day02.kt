import Day02.Result.DRAW
import Day02.Result.LOSS
import Day02.Result.WIN
import Day02.Shape.PAPER
import Day02.Shape.ROCK
import Day02.Shape.SCISSORS

/**
 * [Description](https://adventofcode.com/2022/day/2)
 */
object Day02 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>) = input
        .sumOf { play1stStrategy(it[0], it[2]) }

    override fun part2(input: Iterable<String>) = input
        .sumOf { play2dStrategy(it[0], it[2]) }

    // ---

    enum class Result(val score: Int) {
        LOSS(0), DRAW(3), WIN(6)
    }

    enum class Shape(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        fun shake(other: Shape) = when (other) {
            this -> DRAW
            beat() -> WIN
            else -> LOSS
        }

        fun beat() = when (this) {
            ROCK -> SCISSORS
            SCISSORS -> PAPER
            PAPER -> ROCK
        }
    }

    fun choose(expected: Result, opponent: Shape) = when (expected) {
        DRAW -> opponent
        WIN -> opponent.beat().beat()
        LOSS -> opponent.beat()
    }

    fun match(opponent: Shape, me: Shape): Int {
        return me.score + me.shake(opponent).score
    }

    fun Char.parseShape() = when (this) {
        'A', 'X' -> ROCK
        'C', 'Z' -> SCISSORS
        'B', 'Y' -> PAPER
        else -> throw IllegalArgumentException("Unknown shape: $this")
    }

    fun Char.parseResult() = when (this) {
        'X' -> LOSS
        'Z' -> WIN
        'Y' -> DRAW
        else -> throw IllegalArgumentException("Unknown result: $this")
    }

    fun play1stStrategy(first: Char, second: Char): Int {
        val opponent = first.parseShape()
        val me = second.parseShape()
        return match(opponent, me)
    }

    fun play2dStrategy(first: Char, second: Char): Int {
        val opponent = first.parseShape()
        val result = second.parseResult()
        val me = choose(result, opponent)
        return match(opponent, me)
    }
}