object Day07 : Task {

    @JvmStatic
    fun main(args: Array<String>) = execute()

    override fun part1(input: Iterable<String>): Any {
        val fs = initFileSystem(input)
        return calculatePhase1(fs.root, 100_000)
    }

    override fun part2(input: Iterable<String>): Long {
        val fs = initFileSystem(input)
        val freeSpace = 70_000_000 - fs.root.size
        val needToFree = 30_000_000 - freeSpace
        return calculatePhase2(fs.root, needToFree, fs.root.size)
    }

    private fun initFileSystem(input: Iterable<String>): FileSystem {
        val fs = FileSystem()
        input.forEach(fs::interpret)
        fs.calculateSizes()
        return fs
    }

    private fun calculatePhase1(dir: Dir, threshold: Long): Long {
        return dir.dirs()
            .sumOf {
                (if (it.size <= threshold) it.size else 0) + calculatePhase1(it, threshold)
            }
    }

    private fun calculatePhase2(dir: Dir, threshold: Long, smallest: Long): Long {
        val children = dir.dirs()
            .filter { it.size >= threshold }
            .minOfOrNull { calculatePhase2(it, threshold, smallest) } ?: smallest

        return if (dir.size >= threshold) {
            minOf(children, smallest, dir.size)
        } else {
            minOf(children, smallest)
        }
    }



    sealed class Command
    object Ls : Command()
    object CdTop : Command()
    object CdUp : Command()
    class CdTo(val dirname: String) : Command()


    sealed class Node

    class File(val name: String, val size: Long) : Node()

    class Dir(val name: String, val parent: Dir?) : Node() {
        val nodes: ArrayList<Node> = ArrayList()
        var size: Long = 0L
        fun dirs() = nodes.filterIsInstance<Dir>()
    }

    class FileSystem {
        val root = Dir("/", null)
        private var cursor: Dir = root

        fun interpret(input: String) {
            if (input.startsWith('$')) {
                val cmd = onInput(input.substringAfter(' '))
                execute(cmd)
            } else {
                onOutput(input)
            }
        }

        private fun onInput(raw: String): Command {
            val name = raw.substringBefore(' ')
            val argument = raw.substringAfter(' ')

            return if (name == "cd") {
                when (argument) {
                    "/" -> CdTop
                    ".." -> CdUp
                    else -> CdTo(argument)
                }
            } else Ls
        }

        private fun execute(cmd: Command) {
            cursor = when (cmd) {
                is CdTo -> cursor.dirs().first { it.name == cmd.dirname } as Dir
                CdTop -> root
                CdUp -> cursor.parent ?: root
                Ls -> cursor
            }
        }

        private fun onOutput(output: String) {
            val node = if (output.startsWith("dir")) {
                Dir(output.substringAfter(' '), cursor)
            } else {
                File(
                    name = output.substringAfter(' '),
                    size = output.substringBefore(' ').toLong()
                )
            }

            cursor.nodes.add(node)
        }

        fun calculateSizes(dir: Dir = root): Long {
            dir.size = dir.nodes.sumOf {
                when (it) {
                    is File -> it.size
                    is Dir -> calculateSizes(it)
                }
            }
            return dir.size
        }
    }

}