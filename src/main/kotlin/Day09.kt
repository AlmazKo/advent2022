import kotlin.math.abs

object Day09 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute(false)

    override fun part1(input: Iterable<String>): Any {
        val rope = Rope(2)
        input.map(::parse).forEach(rope::move)
        return rope.visited.size
    }

    override fun part2(input: Iterable<String>): Any {
        val rope = Rope(10)
        input.map(::parse).forEach(rope::move)
        return rope.visited.size
    }

    data class Move(val direction: Dir, val steps: Int)

    data class Coord(var x: Int, var y: Int)

    enum class Dir { U, D, R, L }

    class Rope(len: Int) {
        val chain = Array(len) { Coord(0, 0) }
        var visited = HashSet<Coord>().apply { add(chain.last().copy()) }

        fun move(mv: Move) {
            val head = chain[0]
            when (mv.direction) {
                Dir.U -> repeat(mv.steps) { head.y += 1; rearrange() }
                Dir.D -> repeat(mv.steps) { head.y -= 1; rearrange() }
                Dir.R -> repeat(mv.steps) { head.x += 1; rearrange() }
                Dir.L -> repeat(mv.steps) { head.x -= 1; rearrange() }
            }
        }

        private fun rearrange() {
            (1 until chain.size).forEach {
                val tail = chain[it]
                val head = chain[it - 1]
                val moved = rearrange(tail, head)
                if (moved && it == chain.size - 1) {
                    visited.add(tail.copy())
                }

                if (!moved) return
            }
        }

        private fun rearrange(tail: Coord, head: Coord): Boolean {
            val distX = abs(tail.x - head.x)
            val distY = abs(tail.y - head.y)
            if (distX <= 1 && distY <= 1) {
                return false
            }

            if (distX == 0) {
                tail.y += (if (tail.y > head.y) -1 else 1)
            } else if (distY == 0) {
                tail.x += (if (tail.x > head.x) -1 else 1)
            } else {
                tail.x += (if (tail.x > head.x) -1 else 1)
                tail.y += (if (tail.y > head.y) -1 else 1)
            }

            return true
        }
    }


    // --- util


    fun parse(row: String): Move {
        val split = row.split(' ')
        val dir = Dir.valueOf(split[0]);
        val steps = split[1].toInt()
        return Move(dir, steps)
    }

    fun Rope.dump() {
        println(toDebugString())
    }

    fun Rope.toDebugString() = buildString {
        (10 downTo -10).forEach { y ->
            (-10..10).forEach { x ->
                chain.forEachIndexed { i, c ->
                    if (c.x == x && c.y == y) {
                        if (i == 0) append('H') else append(i)
                        return@forEach
                    }
                }
                append('.')
            }
            append('\n')
        }
    }
}