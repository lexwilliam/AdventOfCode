package day9

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

private val headPath: String = parseInput(File("day9/input.txt").readLines())
private fun followPath(): Int {
    val rope = Array(2) { Point() }
    val tailVisits = mutableSetOf(Point())

    headPath.forEach { direction ->
        rope[0] = rope[0].move(direction)
        rope.indices.windowed(2, 1) { (head, tail) ->
            if (!rope[head].touches(rope[tail])) {
                rope[tail] = rope[tail].moveTowards(rope[head])
            }
        }
        tailVisits += rope.last()
    }
    return tailVisits.size
}

private data class Point(val x: Int = 0, val y: Int = 0) {
    fun move(direction: Char): Point =
        when (direction) {
            'U' -> copy(y = y - 1)
            'D' -> copy(y = y + 1)
            'L' -> copy(x = x - 1)
            'R' -> copy(x = x + 1)
            else -> throw IllegalArgumentException("Unknown Direction: $direction")
        }

    fun moveTowards(other: Point): Point =
        Point(
            (other.x - x).sign + x,
            (other.y - y).sign + y
        )

    fun touches(other: Point): Boolean =
        (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1

}

private fun parseInput(input: List<String>): String =
    input.joinToString("") { row ->
        val direction = row.substringBefore(" ")
        val numberOfMoves = row.substringAfter(' ').toInt()
        direction.repeat(numberOfMoves)
    }
fun main() {
    println(followPath())
}