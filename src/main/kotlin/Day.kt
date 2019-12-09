import java.io.InputStreamReader

abstract class Day<T> {
    fun run() {
        val data =
            this.parse(
                InputStreamReader(
                    this.javaClass.classLoader
                        .getResourceAsStream("${this.javaClass.simpleName}.txt")!!
                ).readLines()
            )


        println("Running ${this.javaClass.simpleName}:")
        println("Part 1: ${part1(data)}")
        println("Part 2: ${part2(data)}")
        println()
    }

    abstract fun part1(data: T): Any
    abstract fun part2(data: T): Any
    abstract fun parse(input: List<String>): T
}
