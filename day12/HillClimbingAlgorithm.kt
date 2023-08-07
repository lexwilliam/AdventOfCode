import java.io.File
import java.util.*

fun part1(filename: String): Int {
    val lines = parseInput(filename)
    val (start, end) = getStartEnd(lines)
    val graph = constructGraph(lines)
    return bfs(graph, start, end)
}

fun part2(filename: String): Int {
    val lines = parseInput(filename)
    val (start, end) = getStartEnd(lines)
    val graph = constructGraph(lines)
    val startCandidates = graph.keys.filter { getElevation(lines[it.second][it.first]) == 0 }
    val stepsRequired = mutableListOf<Int>()
    for (candidate in startCandidates) {
        val steps = bfs(graph, candidate, end)
        if (steps != -1) {
            stepsRequired.add(steps)
        }
    }
    return stepsRequired.minOrNull() ?: -1
}

fun parseInput(filename: String): List<String> {
    return File(filename).readLines()
}

fun getStartEnd(lines: List<String>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    var start = Pair(-1, -1)
    var end = Pair(-1, -1)
    for ((y, line) in lines.withIndex()) {
        val startX = line.indexOf("S")
        if (startX != -1) {
            start = Pair(startX, y)
        }
        val endX = line.indexOf("E")
        if (endX != -1) {
            end = Pair(endX, y)
        }
    }
    return Pair(start, end)
}

fun getElevation(elevation: Char): Int {
    return when (elevation) {
        'S' -> getElevation('a')
        'E' -> getElevation('z')
        else -> elevation - 'a'
    }
}

fun constructGraph(lines: List<String>): Map<Pair<Int, Int>, Set<Pair<Int, Int>>> {
    val graph = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
    for ((y, line) in lines.withIndex()) {
        for ((x, char) in line.withIndex()) {
            val coord = Pair(x, y)
            val children = mutableSetOf<Pair<Int, Int>>()
            val candidates = listOf(
                Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1)
            ).map { (dx, dy) -> Pair(x + dx, y + dy) }.filter { (x1, y1) ->
                0 <= x1 && x1 < line.length && 0 <= y1 && y1 < lines.size
            }
            for ((x1, y1) in candidates) {
                if (getElevation(lines[y1][x1]) - getElevation(char) <= 1) {
                    children.add(Pair(x1, y1))
                }
            }
            graph[coord] = children
        }
    }
    return graph
}

fun bfs(graph: Map<Pair<Int, Int>, Set<Pair<Int, Int>>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val visited = mutableMapOf(start to 0)
    val queue: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
    queue.add(Pair(start, 0))
    while (queue.isNotEmpty()) {
        val (current, steps) = queue.poll()
        if (current == end) {
            return steps
        }
        for (neighbour in graph[current]!!) {
            if (neighbour !in visited) {
                visited[neighbour] = steps + 1
                queue.add(Pair(neighbour, steps + 1))
            }
        }
    }
    return -1
}

fun main() {
    val inputPath = "day12/input.txt"
    println(part1(inputPath))
    println(part2(inputPath))
}
