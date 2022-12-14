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

    override fun part2(input: Iterable<String>): Any {
        val map = parse(input)
        val copter = Copter2(map)
        val bestScore = copter.launch()
        return bestScore
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

    class Copter2(private val grid: Map) {
        private val width = grid.first().size
        private val height = grid.size

        fun launch(): Int {
            return (0..(width * height)).iterator().asSequence()
                .map { lookup(it % width, it / height) }
                .max()
        }

        private fun lookup(x: Int, y: Int): Int {
            if (x == 0 || y == 0) return 0

            val current = grid[y][x]
            val north = toNorth(x, y, current)
            val south = toSouth(x, y, current)
            val west = toWest(x, y, current)
            val east = toEast(x, y, current)
            return north * south * west * east
        }

        private fun toNorth(x: Int, y: Int, max: Int): Int {
            var distance = 0
            for (y in (y - 1)downTo 0) {
                distance += 1
                if (grid[y][x] >= max) break
            }
            return distance
        }

        private fun toSouth(x: Int, y: Int, max: Int): Int {
            var distance = 0
            for (y in (y + 1) until height) {
                distance += 1
                if (grid[y][x] >= max) break
            }
            return distance
        }

        private fun toEast(x: Int, y: Int, max: Int): Int {
            var distance = 0
            for (x in (x + 1) until width) {
                distance += 1
                if (grid[y][x] >= max) break
            }
            return distance
        }

        private fun toWest(x: Int, y: Int, max: Int): Int {
            var distance = 0
            for (x in (x - 1) downTo 0) {
                distance += 1
                if (grid[y][x] >= max) break
            }
            return distance
        }

    }

    // --- util


    fun parse(input: Iterable<String>): Map {
        return input.map {
            it.toCharArray().map { it.code - 48 }.toIntArray()
        }.toTypedArray()
    }
}