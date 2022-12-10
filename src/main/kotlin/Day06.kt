object Day06 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val index = input.first().toCharArray().toList()
            .windowed(4, 1)
            .indexOfFirst { it.distinct().size == 4 }
        return index + 4
    }

    override fun part2(input: Iterable<String>): Any {
        return super.part2(input)
    }


}