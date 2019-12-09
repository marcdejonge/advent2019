@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

class Day02 : Day<List<Int>>() {
    override fun part1(data: List<Int>): Any =
        calc(data, 12, 2)

    override fun part2(data: List<Int>): Any {
        for (sum in generateSequence(1) { it + 1 }) {
            for (in1 in 1..sum) {
                val in2 = sum - in1
                val result = calc(data, in1, in2)
                if (result == 19690720) {
                    return in1 to in2
                }
            }
        }

        throw IllegalStateException()
    }

    fun calc(input: List<Int>, in1: Int, in2: Int): Int {
        val data = input.toMutableList()
        data[1] = in1
        data[2] = in2

        loop@ for (pc in generateSequence(0) { it + 4 }) {
            when (data[pc]) {
                1 -> data[data[pc + 3]] =
                    data[data[pc + 1]] + data[data[pc + 2]]
                2 -> data[data[pc + 3]] =
                    data[data[pc + 1]] * data[data[pc + 2]]
                99 -> break@loop
                else -> throw IllegalArgumentException("Unexpected input")
            }
        }


        return data[0]
    }

    override fun parse(input: List<String>): List<Int> =
        input[0].split(",").map { Integer.parseInt(it) }
}

fun main() {
    Day02().run()
}