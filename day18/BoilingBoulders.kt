package day18

import java.io.File

data class SmallCube(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun fromString(coords: String): SmallCube =
            with(coords.split(",").map(Integer::parseInt)) {
                SmallCube(this[0], this[1], this[2])
            }
    }

    var exposedSides: Int = 6

    fun computeExposedSides(others: Set<SmallCube>): Int {
        exposedSides = 6 - neighbors().filter {
            others.contains(it)
        }.size

        return exposedSides
    }

    private fun neighbors(): Set<SmallCube> = setOf(
        SmallCube(x + 1, y, z), SmallCube(x - 1, y, z),
        SmallCube(x, y + 1, z), SmallCube(x, y - 1, z),
        SmallCube(x, y, z + 1), SmallCube(x, y, z - 1)
    )
}
fun main() {
    val input = File("day18/input.txt").readLines()
    val cubes = input.map(SmallCube::fromString).toSet()
    cubes.forEach { it.computeExposedSides(cubes) }

    println(cubes.sumOf { it.exposedSides })
}