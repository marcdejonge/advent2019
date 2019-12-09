class Day01 : Day<List<Int>>() {
    override fun part1(data: List<Int>) =
        data.map { it / 3 - 2 }.sum()

    override fun part2(data: List<Int>) =
        data.map { fuel(it) }.sum()

    fun fuel(weight: Int): Int {
        val fuel = weight / 3 - 2
        return if (fuel <= 0)
            0
        else
            fuel + fuel(fuel)
    }

    override fun parse(input: List<String>): List<Int> = input.map { Integer.parseInt(it) }
}

fun main() {
    Day01().run()
}