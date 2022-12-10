object Day04 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>) = input
        .map { it.parseRow() }
        .count { (first, second) ->
            first.inside(second) || second.inside(first)
        }

    override fun part2(input: Iterable<String>) = input
        .map { it.parseRow() }
        .count { (first, second) ->
            first.intersect(second)
        }


    fun String.parseRow(): Pair<IntRange, IntRange> {
        return substringBefore(',').parseRange() to
            substringAfter(',').parseRange()
    }

    fun String.parseRange(): IntRange {
        return split('-').run { this[0].toInt()..this[1].toInt() }
    }

    fun IntRange.inside(other: IntRange): Boolean {
        return first <= other.first && last >= other.last
    }

    fun IntRange.intersect(other: IntRange): Boolean {
        return first <= other.last && last >= other.first
    }

}