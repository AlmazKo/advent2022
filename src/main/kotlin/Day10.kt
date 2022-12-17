object Day10 : Task {
    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val cpu = CPU()
        val interested = setOf(20, 60, 100, 140, 180, 220)

        return input.asSequence().map(::parse)
            .onEach(cpu::accept)
            .flatMap { 1..it.cycles }
            .onEach(cpu::tick)
            .filter { cpu.cycle in interested }
            .sumOf { cpu.register * cpu.cycle }
    }

    class CPU {
        var cycle = 1
        var register = 1
        private lateinit var instruction: Instruction

        fun accept(i: Instruction) {
            instruction = i
        }

        fun tick(step: Int) {
            if (step == 2 && instruction is AddX) {
                register += (instruction as AddX).value
            }
            cycle++
        }
    }

    sealed class Instruction(val cycles: Int)

    object Noop : Instruction(1)

    data class AddX(val value: Int) : Instruction(2)


    // --- util


    private fun parse(it: String): Instruction {
        val chunks = it.split(' ')
        return when (chunks[0]) {
            "noop" -> Noop
            "addx" -> AddX(chunks[1].toInt())
            else -> throw IllegalArgumentException()
        }
    }

}