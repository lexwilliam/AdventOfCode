package day15

import java.io.File

fun String.getInts(): List<Int> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()

fun part1(input: List<String>, pointToCheck: Int): Int {

    val map = input.map { it.getInts() }.map { (it[0] to it[1]) to (it[2] to it[3]) }
    val min = minOf(map.minOf { it.first.first }, map.minOf { it.second.first })
    val max = maxOf(map.maxOf { it.first.first }, map.maxOf { it.second.first })
    val maxDist = map.maxOf { distance(it.first, it.second) }

    val res = map.flatMap { pair ->
        (min - maxDist..max + maxDist).filter { x -> (distance(pair.first, x to pointToCheck) <= distance(pair.first, pair.second)) }
    }.toSet().size

    val sensorAndBeacons = map.filter { it.first.second == pointToCheck || it.second.second == pointToCheck }.map { it.second }.toSet().size
    return res - sensorAndBeacons
}

fun part2(input: List<String>, maxVal: Int): Long {

    val map = input.map { it.getInts() }.map { (it[0] to it[1]) to (it[2] to it[3]) }
    val pointToDist = map.map { it.first to distance(it.first, it.second) }
    val pair = map.firstNotNullOf {
        pointsOutside(it.first, it.second, maxVal).firstOrNull { pair -> pointToDist.none { p -> distance(pair, p.first) <= p.second } }
    }
    return pair.first.toLong() * maxVal + pair.second
}


fun pointsOutside(p1: Pair<Int, Int>, p2: Pair<Int, Int>, maxVal: Int): Sequence<Pair<Int, Int>> {
    val dist = distance(p1, p2)
    fun inRange(it: Pair<Int, Int>) = it.first in 0..maxVal && it.second >= 0 && it.second <= maxVal
    val seqEdge1 = generateSequence(p1.first to p1.second - dist - 1) { p -> p.first + 1 to p.second + 1 }.takeWhile { it.second <= p1.second }
        .filter { inRange(it) }
    val seqEdge2 = generateSequence(p1.first to p1.second - dist - 1) { p -> p.first - 1 to p.second + 1 }.takeWhile { it.second <= p1.second }
        .filter { inRange(it) }
    val seqEdge3 = generateSequence(p1.first to p1.second + dist + 1) { p -> p.first + 1 to p.second - 1 }.takeWhile { it.second >= p1.second }
        .filter { inRange(it) }
    val seqEdge4 = generateSequence(p1.first to p1.second + dist + 1) { p -> p.first - 1 to p.second - 1 }.takeWhile { it.second >= p1.second }
        .filter { inRange(it) }
    return seqEdge1 + seqEdge2 + seqEdge3 + seqEdge4
}

fun distance(p0: Pair<Int, Int>, p1: Pair<Int, Int>): Int = (kotlin.math.abs(p0.first - p1.first) + kotlin.math.abs(p0.second - p1.second))

fun main() {
    val input = File("day15/input.txt").readLines()

    println(part1(input, 10))
    println(part2(input, 2000000))
}