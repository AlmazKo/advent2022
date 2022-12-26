object Day25 : Task {
    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        return input.sumOf { it.parseSnafu() }.toSnafu()
    }


    // -- util


    private val digits = charArrayOf('0', '1', '2', '=', '-')

    private fun String.parseSnafu(): Long {
        var result = 0L
        for (char in this) {
            val i = when (char) {
                '0' -> 0
                '1' -> 1
                '2' -> 2
                '=' -> -2
                '-' -> -1
                else -> throw IllegalArgumentException("$char")
            }
            result = result * 5 + i
        }
        return result
    }

    private fun Long.toSnafu(): String {
        val result = CharArray(20)
        var pos = result.size
        var i = this
        while (i > 0) {
            val digit = digits[(i % 5).toInt()]
            result[--pos] = digit
            if (digit == '=') i += 2
            if (digit == '-') i += 1
            i /= 5
        }

        return String(result, pos, result.size - pos)
    }
}