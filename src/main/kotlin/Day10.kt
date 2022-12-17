object Day10 : Task {
    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val cpu = CPU()
        val interested = setOf(20, 60, 100, 140, 180, 220)

        return input.asSequence().flatMap(::parse)
            .onEach(cpu::tick)
            .filter { cpu.cycle in interested }
            .sumOf { cpu.register * cpu.cycle }
    }

    override fun part2(input: Iterable<String>): Any {
        val cpu = CPU()
        val crt = CRT()

        input.asSequence().flatMap(::parse)
            .forEach {
                crt.onTick(cpu)
                cpu.tick(it)
            }

        return crt.buffer
    }


    class CPU {
        var cycle = 1
        var register = 1

        fun tick(op: OpCode) {
            if (op is X) {
                register += op.value
            }
            cycle++
        }
    }


    class CRT {
        private val width = 40
        val buffer = StringBuilder()

        fun onTick(cpu: CPU) {
            val cursor = (cpu.cycle - 1) % width
            if (cursor == 0) {
                buffer.append('\n')
            }

            if (cursor in cpu.register - 1..cpu.register + 1) {
                buffer.append('#')
            } else {
                buffer.append('.')
            }
        }
    }

    sealed class OpCode
    object Noop : OpCode()
    object Add : OpCode()
    data class X(val value: Int) : OpCode()


    // --- util


    private fun parse(it: String): List<OpCode> {
        val chunks = it.split(' ')
        return when (chunks[0]) {
            "noop" -> listOf(Noop)
            "addx" -> listOf(Add, X(chunks[1].toInt()))
            else -> throw IllegalArgumentException()
        }
    }
}