typealias Map = Array<IntArray>

object Day08 : Task {
    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val map = parse(input)
        val copter = Copter(map)
        val visible = copter.launch()
        return visible.sumOf { it.count { it > 0 } }
    }

    class Copter(private val grid: Map) {
        private val visible: Map = Array(grid.size) { IntArray(grid.first().size) }
        private val width = grid.first().size
        private val height = grid.size
        private var highestTree: Int = -1

        fun launch(): Array<IntArray> {
            repeat(height) { y ->
                highestTree = -1
                toEast(y)
                highestTree = -1
                toWest(y)
            }

            repeat(width) { x ->
                highestTree = -1
                toSouth(x)
                highestTree = -1
                toNorth(x)
            }
            return visible
        }

        private fun toEast(y: Int) = repeat(width) { onTree(it, y) }

        private fun toWest(y: Int) = repeat(width) { onTree(width - it - 1, y) }

        private fun toSouth(x: Int) = repeat(height) { onTree(x, it) }

        private fun toNorth(x: Int) = repeat(height) { onTree(x, height - it - 1) }

        private fun onTree(x: Int, y: Int) {
            val t = grid[y][x]
            if (t > highestTree) {
                visible[y][x] = 1
                highestTree = t
            }
        }
    }


    // --- util


    fun parse(input: Iterable<String>): Map {
        return input.map {
            it.toCharArray().map { it.code - 48 }.toIntArray()
        }.toTypedArray()
    }
}