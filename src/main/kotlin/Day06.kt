object Day06 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        return find(input.first(), 4)
    }

    override fun part2(input: Iterable<String>): Any {
        return find(input.first(), 14)
    }


    private fun find(input: String, windowSize: Int): Int {
        val index = input.toCharArray().toList()
            .windowed(windowSize, 1)
            .indexOfFirst { it.distinct().size == windowSize }
        return index + windowSize
    }
}