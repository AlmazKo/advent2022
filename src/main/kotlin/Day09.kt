import java.lang.Math.abs

object Day09 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val rope = Rope(0, 0, 0, 0)
        input.map { it.parseMove() }
            .forEach { rope.move(it.direction, it.steps) }
        return rope.visited.size
    }

    data class Move(val direction: Dir, val steps: Int)

    data class Coord(val x: Int, val y: Int)

    enum class Dir { U, D, R, L }

    class Rope(
        var headX: Int,
        var headY: Int,
        var tailX: Int,
        var tailY: Int
    ) {
        var visited = HashSet<Coord>().apply { add(Coord(tailX, tailY)) }

        fun move(dir: Dir, moves: Int) {
            when (dir) {
                Dir.U -> repeat(moves) { headY += 1; moveTail() }
                Dir.D -> repeat(moves) { headY -= 1; moveTail() }
                Dir.R -> repeat(moves) { headX += 1; moveTail() }
                Dir.L -> repeat(moves) { headX -= 1; moveTail() }
            }
        }

        private fun moveTail() {
            val distX = abs(tailX - headX)
            val distY = abs(tailY - headY)
            if (distX <= 1 && distY <= 1) {
                return
            }

            if (distX == 0) {
                tailY += (if (tailY > headY) -1 else 1)
            } else if (distY == 0) {
                tailX += (if (tailX > headX) -1 else 1)
            } else if (distX == 1) {
                tailX = headX
                tailY += (if (tailY > headY) -1 else 1)
            } else {
                tailY = headY
                tailX += (if (tailX > headX) -1 else 1)
            }

            visited.add(Coord(tailX, tailY))
        }
    }


    // --- util


    fun String.parseMove(): Move {
        val split = split(' ')
        val dir = Dir.valueOf(split[0]);
        val steps = split[1].toInt()
        return Move(dir, steps)
    }
}