package day16

import java.io.File
import kotlin.math.max

class ValveMap(data: List<String>) {
    private val valves = data.map { Valve.fromInput(it) }.associateBy { it.name }

    init {
        calculateShortestPaths()
    }

    fun findMaxFlowRate(start: String, minutes: Int, withElephant: Boolean = false): Int {
        return depthFirstSearch(
            originalStart = start,
            currentFlow = 0,
            maxFlow = 0,
            currentValve = start,
            visited = mutableSetOf(),
            tick = 0,
            minutes = minutes,
            runTwice = withElephant
        )
    }

    private fun calculateShortestPaths() {
        for (k in valves.keys) {
            for (i in valves.keys) {
                for (j in valves.keys) {
                    val ik = valves[i]!!.shortestPaths[k] ?: Short.MAX_VALUE.toInt()
                    val kj = valves[k]!!.shortestPaths[j] ?: Short.MAX_VALUE.toInt()
                    val ij = valves[i]!!.shortestPaths[j] ?: Short.MAX_VALUE.toInt()
                    if (ij > ik + kj)
                        valves[i]!!.shortestPaths[j] = ik + kj
                }
            }
        }

        valves.values.filter { v -> v.flowRate == 0 }.map(Valve::name).forEach { zeroValueValve ->
            for (valve in valves) {
                valve.value.shortestPaths.remove(zeroValueValve)
            }
        }
    }

    private fun depthFirstSearch(originalStart: String, currentFlow: Int, maxFlow: Int, currentValve: String, visited: Set<String>, tick: Int, minutes: Int, runTwice: Boolean): Int {
        var newMaxFlow = max(maxFlow, currentFlow)
        for ((valve, distance) in valves[currentValve]?.shortestPaths!!) {
            if (!visited.contains(valve) && tick + distance + 1 < minutes) {
                newMaxFlow = depthFirstSearch(
                    originalStart = originalStart,
                    currentFlow = currentFlow + (minutes - tick - distance - 1) * valves[valve]?.flowRate!!,
                    maxFlow = newMaxFlow,
                    currentValve = valve,
                    visited = visited.union(listOf(valve)),
                    tick = tick + distance + 1,
                    minutes = minutes,
                    runTwice = runTwice
                )
            }
        }

        return if (runTwice) {
            depthFirstSearch(
                originalStart,
                currentFlow,
                newMaxFlow,
                originalStart,
                visited,
                tick = 0,
                minutes,
                runTwice = false // Make sure we don't runTwice again, otherwise infinite recursion...
            )
        } else {
            newMaxFlow
        }
    }
}
data class Valve(val name: String, val flowRate: Int, val reachableValves: List<String>) {
    val shortestPaths: MutableMap<String, Int> = reachableValves.associateWith { 1 }.toMutableMap()

    companion object {
        fun fromInput(inputLine: String): Valve {
            val lineRegex = Regex("Valve (.*) has flow.*=(.*);.*valve[s]? (.*)")
            val parts = lineRegex.find(inputLine)?.groupValues?.drop(1)
                ?: throw IllegalArgumentException("Can't parse input line [$inputLine]")
            return Valve(parts[0], Integer.parseInt(parts[1]), parts[2].split(",").map { it.trim() })
        }
    }

}

fun main() {
    val input = File("day16/input.txt").readLines()
    val valveMap = ValveMap(input)
    println(valveMap.findMaxFlowRate("AA", 30))
}