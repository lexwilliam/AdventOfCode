package day7

import java.io.File

fun main() {
    val commands = File("C:\\Users\\willi\\IdeaProjects\\AdventOfCode\\day7\\input.txt").readLines()

    val sizes = mutableMapOf<String, Int>()
    val stack = mutableListOf<String>()

    for (command in commands) {
        when {
            command.startsWith("$ ls") || command.startsWith("dir") -> continue
            command.startsWith("$ cd") -> {
                val dest = command.split(" ")[2]
                if (dest == "..") {
                    stack.removeAt(stack.lastIndex)
                } else {
                    val path = if (stack.isNotEmpty()) "${stack.last()}_$dest" else dest
                    stack.add(path)
                }
            }
            else -> {
                val (size, file) = command.split(" ")
                for (path in stack) {
                    sizes[path] = sizes.getOrDefault(path, 0) + size.toInt()
                }
            }
        }
    }

    val neededSize = 30000000 - (70000000 - sizes["/"]!!)
    val sortedSizes = sizes.values.sorted()

    val sumTotalSizes = sizes.values.filter { it <= 100000 }.sum()

    println("Sum of total sizes of directories with size at most 100000: $sumTotalSizes") // task 1

    val size = sortedSizes.firstOrNull { it > neededSize } ?: sortedSizes.last()
    println("Smallest size larger than $neededSize: $size") // task 2
}
