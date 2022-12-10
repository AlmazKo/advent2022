object Day02p1 : Task {
    override fun name() = "day02"

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>) = input
        .sumOf { match(it[2].parse(), it[0].parse()) }

    const val WIN = 6
    const val DRAW = 3
    const val LOSS = 0

    sealed class Shape(val score: Int) {
        fun shake(other: Shape) = when (other) {
            this -> DRAW
            beat() -> WIN
            else -> LOSS
        }

        private fun beat() = when (this) {
            Rock -> Scissors
            Scissors -> Paper
            Paper -> Rock
        }
    }

    object Rock : Shape(1)
    object Paper : Shape(2)
    object Scissors : Shape(3)

    private fun match(me: Shape, opponent: Shape): Int {
        return me.score + me.shake(opponent)
    }

    private fun Char.parse() = when (this) {
        'A', 'X' -> Rock
        'C', 'Z' -> Scissors
        'B', 'Y' -> Paper
        else -> throw IllegalArgumentException("Unknown character: $this")
    }
}