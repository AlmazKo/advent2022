import java.util.Deque
import java.util.LinkedList
import java.util.TreeMap

object Day05 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val crates = TreeMap<Int, Deque<Char>>()
        input.forEach {
            if (it.startsWith('m')) {
                val cmd = it.parseCommand()
                val from = crates[cmd.from]!!
                val to = crates[cmd.to]!!
                repeat(cmd.size) {
                    to.push(from.poll()!!)
                }
            } else {
                it.forEachIndexed { i, c ->
                    if (c in 'A'..'Z') {
                        crates.getOrPut((i + 1) / 4 + 1, ::LinkedList).add(c)
                    }
                }
            }
        }

        return crates.values.map { it.peek() }.joinToString("")
    }

    override fun part2(input: Iterable<String>): Any {
        return super.part2(input)
    }

    fun String.parseCommand(): Command {
        val chunks = split(' ')
        return Command(chunks[1].toInt(), chunks[3].toInt(), chunks[5].toInt())
    }


    data class Command(
        val size: Int,
        val from: Int,
        val to: Int
    )


}