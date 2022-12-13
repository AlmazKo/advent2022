import java.util.Deque
import java.util.LinkedList
import java.util.SortedMap
import java.util.TreeMap

object Day05 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val stacks = parseCrates(input)
        parseCommands(input).forEach { (size, from, to) ->
            move(stacks[from]!!, stacks[to]!!, size)
        }
        return stacks.top()
    }

    override fun part2(input: Iterable<String>): Any {
        val stacks = parseCrates(input)
        parseCommands(input).forEach { (size, from, to) ->
            move2(stacks[from]!!, stacks[to]!!, size)
        }
        return stacks.top()
    }

    private fun move(from: Deque<Char>, to: Deque<Char>, size: Int) {
        repeat(size) {
            to.push(from.poll()!!)
        }
    }

    private fun move2(from: Deque<Char>, to: Deque<Char>, size: Int) {
        LinkedList<Char>().apply {
            repeat(size) { push(from.poll()) }
            repeat(size) { to.push(poll()) }
        }
    }

    data class Command(
        val size: Int,
        val from: Int,
        val to: Int
    )


    // --- util


    private fun parseCommands(input: Iterable<String>): Sequence<Command> {
        return input.asSequence()
            .filter { it.startsWith('m') }
            .map { it.toCommand() }
    }

    private fun parseCrates(input: Iterable<String>): SortedMap<Int, Deque<Char>> {
        val crates = TreeMap<Int, Deque<Char>>()
        input.takeWhile(String::isNotEmpty)
            .forEach {
                it.forEachIndexed { i, char ->
                    if (char in 'A'..'Z') {
                        crates.getOrPut((i + 1) / 4 + 1, ::LinkedList).add(char)
                    }
                }
            }

        return crates
    }

    private fun SortedMap<Int, Deque<Char>>.top(): String {
        return values.map { it.peek() }.joinToString("")
    }

    private fun String.toCommand(): Command {
        val chunks = split(' ')
        return Command(chunks[1].toInt(), chunks[3].toInt(), chunks[5].toInt())
    }

}