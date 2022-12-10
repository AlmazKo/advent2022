object Day03 : Task {
    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>) = input
        .map {
            intersect(it.subSequence(0, it.length / 2), it.subSequence(it.length / 2, it.length))
        }
        .sumOf { it.priority() }


    override fun part2(input: Iterable<String>) = input
        .chunked(3)
        .map { intersect(it[0], it[1], it[2]) }
        .sumOf { it.priority() }


    fun intersect(first: CharSequence, second: CharSequence): Char {
        return first.asSequence().first { second.contains(it) }
    }

    fun intersect(first: CharSequence, second: CharSequence, third: CharSequence): Char {
        return first.asSequence().first { second.contains(it) && third.contains(it) }
    }

    fun Char.priority(): Int {
        return if (this >= 'a') {
            this - 'a' + 1
        } else {
            this - 'A' + 27
        }
    }
}