import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

interface Task {

    fun part1(input: Iterable<String>): Any = "No result"

    fun part2(input: Iterable<String>): Any = "No result"

    fun readTestInput() = File(javaClass.simpleName.lowercase() + "_test.txt")
        .readLines()

    fun readInput() = File(javaClass.simpleName.lowercase() + ".txt")
        .readLines()

    fun execute(test: Boolean = false) {
        val input = if (test) readTestInput() else readInput()

        println("Part 1: " + part1(input))
        println("Part 2: " + part2(input))
    }

    fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')
}