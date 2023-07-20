package day10

import java.io.File

private fun sigStrength(list: List<Int>, i: Int) = list[i - 2] * i

fun main() {
    val list = mutableListOf<Int>()
    var x = 1

    File("day10/input.txt").readLines().forEach { line ->
        list.add(x)
        if (line.startsWith("addx")) {
            val (_, inc) = line.split(" ")
            x += inc.toInt()
            list.add(x)
        }
    }
    val relevant = setOf(20, 60, 100, 140, 180, 220)
    print(relevant.sumOf { sigStrength(list, it) })
}