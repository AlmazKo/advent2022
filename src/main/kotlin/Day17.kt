object Day17 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute(false)

    override fun part1(input: Iterable<String>): Any {
        val directionsOrigin = input.first().map { if (it == '<') Dir.Left else Dir.Right }

        val directions = generateSequence(0) { if (it < directionsOrigin.size - 1) it + 1 else 0 }
            .map { directionsOrigin[it] }
            .iterator()

        val shapes = generateSequence(0) { if (it < SHAPE_TYPES.size - 1) it + 1 else 0 }
            .map { SHAPE_TYPES[it] }
            .iterator()

        val rocks = 2022
        val tunnel = Tunnel(7)
        repeat(rocks) {
            tunnel.place(shapes.next())

            directions.forEach {
                tunnel.move(it)
                if (!tunnel.move(Dir.Down)) {
                    tunnel.seal()
                    return@repeat
                }
            }
        }

        return tunnel.peak + 1
    }


    const val ONE = 1.toByte()

    class Shape(vararg rawData: List<Int>) {
        val data = Array<ByteArray>(rawData.size) {
            rawData[it].map { it.toByte() }.toByteArray()
        }

        val height: Int = data.size
        val width: Int = if (height == 0) 0 else data[0].size


        operator fun get(x: Int, y: Int): Byte {
            val row = data.getOrNull(y) ?: return -1
            return row.getOrNull(x) ?: return -1
        }

        override fun toString() = buildString {
            data.forEach { row ->
                row.forEach {
                    if (it == ONE) {
                        append('@')
                    } else {
                        append('.')
                    }
                }
                append('\n')
            }

        }
    }

    private val SHAPE_TYPES = listOf(
        Shape(
            listOf(1, 1, 1, 1)
        ),
        Shape(
            listOf(0, 1, 0),
            listOf(1, 1, 1),
            listOf(0, 1, 0),
        ),
        Shape(
            listOf(0, 0, 1),
            listOf(0, 0, 1),
            listOf(1, 1, 1),
        ),
        Shape(
            listOf(1),
            listOf(1),
            listOf(1),
            listOf(1),
        ),
        Shape(
            listOf(1, 1),
            listOf(1, 1),
        )
    )

    val zeroShape = Shape()

    enum class Dir { Left, Right, Down }

    class Tunnel(val width: Int) {
        var peak = -1
        private val data = ArrayList<ByteArray>()
        private var shape: Shape = zeroShape
        private var shapeLeftX = 0
        private var shapeTopY = 0

        fun place(shape: Shape) {
            this.shape = shape
            shapeLeftX = 2
            shapeTopY = peak + 3 + shape.height
            tryAllocate(shapeTopY + 1)
        }

        fun move(dir: Dir): Boolean {
            return when (dir) {
                Dir.Left -> left()
                Dir.Right -> right()
                Dir.Down -> down()
            }
        }

        private fun right(): Boolean {
            shape.data.forEachIndexed { index, it ->
                val sx = shapeLeftX + it.indexOfLast { it == ONE }
                val sy = shapeTopY - index
                if (!isFree(sx + 1, sy)) return false
            }

            shapeLeftX++
            return true
        }

        private fun left(): Boolean {
            shape.data.forEachIndexed { index, row ->
                val sx = shapeLeftX + row.indexOfFirst { it == ONE }
                val sy = shapeTopY - index
                if (!isFree(sx - 1, sy)) return false
            }

            shapeLeftX--
            return true
        }

        private fun down(): Boolean {
            repeat(shape.width) { x ->
                val sx = shapeLeftX + x
                val sy = shapeTopY - shape.data.indexOfLast { it[x] == ONE }
                if (!isFree(sx, sy - 1)) return false
            }

            shapeTopY--
            return true
        }

        fun seal() {
            shape.data.forEachIndexed { y, row ->
                row.forEachIndexed { x, value ->
                    if (value == ONE) {
                        data[shapeTopY - y][shapeLeftX + x] = ONE
                    }
                }
            }
            peak = maxOf(shapeTopY, peak)
            shape = zeroShape
        }

        private fun isFree(x: Int, y: Int): Boolean {
            return (x in 0 until width) && (y >= 0) && data[y][x] != ONE
        }

        private fun tryAllocate(y: Int) {
            if (y < data.size) return

            repeat(y - data.size) {
                data.add(ByteArray(width))
            }
        }

        override fun toString() = buildString {
            data.asReversed().forEachIndexed { yy, row ->
                val y = data.size - yy - 1
                row.forEachIndexed { x, value ->
                    if (value == ONE) {
                        append('#')
                    } else if (shape[x - shapeLeftX, shapeTopY - y] == ONE) {
                        append('@')
                    } else {
                        append('.')
                    }
                }
                append('\n')
            }
        }
    }
}