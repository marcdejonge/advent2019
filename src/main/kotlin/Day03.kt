import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

class Day03 : Day<List<List<Place>>>() {
    override fun part1(data: List<List<Place>>) =
        data[0].flatMap { firstLine -> data[1].map { it.getCross(firstLine) } }
            .filterNotNull()
            .map { (x, y, _) -> abs(x) + abs(y) }
            .min()!!

    override fun part2(data: List<List<Place>>): Any =
        data[0].flatMap { firstLine -> data[1].map { it.getCross(firstLine) } }
            .filterNotNull()
            .map { it.steps }
            .min()!!

    override fun parse(input: List<String>) =
        input.map { line ->
            val list = ArrayList<Place>()
            var curr: Place = Point(0, 0, 0)
            line.split(",").forEach { command ->
                curr = curr.next(command)
                list.add(curr)
            }
            list
        }
}

fun main() {
    Day03().run()
}

interface Place {
    val x: Int
    val y: Int
    val steps: Int

    fun getCross(other: Place): Point? = null

    fun next(command: String): Place {
        val commandSteps = Integer.parseInt(command.substring(1))
        return when (command[0]) {
            'U' -> VerticalLine(x, y, y + commandSteps, steps + commandSteps)
            'D' -> VerticalLine(x, y, y - commandSteps, steps + commandSteps)
            'L' -> HorizontalLine(x, x - commandSteps, y, steps + commandSteps)
            'R' -> HorizontalLine(x, x + commandSteps, y, steps + commandSteps)
            else -> throw IllegalArgumentException(command)
        }
    }
}

data class Point(override val x: Int, override val y: Int, override val steps: Int) : Place

data class HorizontalLine(val x1: Int, val x2: Int, override val y: Int, override val steps: Int) : Place {
    override val x get() = x2

    override fun getCross(other: Place) =
        if (other is VerticalLine
            && min(other.y1, other.y2) < y && max(other.y1, other.y2) > y
            && min(x1, x2) < other.x && max(x1, x2) > other.x
        )
            Point(other.x, y, steps + other.steps - abs(other.x - x2) - abs(y - other.y2))
        else
            null
}

data class VerticalLine(override val x: Int, val y1: Int, val y2: Int, override val steps: Int) : Place {
    override val y get() = y2

    override fun getCross(other: Place) =
        if (other is HorizontalLine)
            other.getCross(this)
        else
            null
}
