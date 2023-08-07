package day19

import java.io.File
data class Blueprint(
    val id: Int,
    val ore: Ore,
    val clay: Clay,
    val obsidian: Obsidian,
    val geode: Geode
) {
    data class Ore(var oreCost: Int, var many: Int = 1, var total: Int = 0, var max: Int = 0)
    data class Clay(var oreCost: Int, var many: Int = 0, var total: Int = 0, var max: Int = 0)
    data class Obsidian(var oreCost: Int, var clayCost: Int, var many: Int = 0, var total: Int = 0, var max: Int = 0)
    data class Geode(var oreCost: Int, var obsidianCost: Int, var many: Int = 0, var total: Int = 0, var max: Int = 0)
}

fun String.getInts(): List<Int> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()

fun fromInputToBlueprint(input: List<String>): Blueprint {
    return Blueprint(
        id = input[0].getInts().first(),
        ore = Blueprint.Ore(input[1].getInts().first()),
        clay = Blueprint.Clay(input[2].getInts().first()),
        obsidian = Blueprint.Obsidian(input[3].getInts()[0], input[3].getInts()[1]),
        geode = Blueprint.Geode(input[4].getInts()[0], input[4].getInts()[1]),
    )
}

fun main() {
    val input = File("day19/input.txt").readLines()
    val blueprints: MutableList<Blueprint> = mutableListOf()
    var temp = mutableListOf<String>()
    input.forEach { line ->
        if (line.isNotEmpty()) {
            temp.add(line)
        } else {
            blueprints.add(fromInputToBlueprint(temp))
            temp = mutableListOf()
        }
    }
    blueprints.add(fromInputToBlueprint(temp))
    val time = 24
    val excess = mutableListOf(0, 0)

    blueprints.forEach { blueprint ->
        repeat(time) {
            blueprint.ore.total += blueprint.ore.many + blueprint.ore.total
            blueprint.clay.total = blueprint.clay.many + blueprint.clay.total
            blueprint.obsidian.total = blueprint.obsidian.many + blueprint.obsidian.total
            blueprint.geode.total = blueprint.geode.many + blueprint.geode.total

            if (blueprint.ore.total >= blueprint.geode.oreCost && blueprint.obsidian.total >= blueprint.geode.obsidianCost) {
                blueprint.ore.total -= blueprint.geode.oreCost
                blueprint.obsidian.total -= blueprint.geode.obsidianCost
                blueprint.geode.many += 1
            } else if (blueprint.ore.total >= blueprint.obsidian.oreCost && blueprint.clay.total >= blueprint.obsidian.clayCost || excess[1] != 0) {
                blueprint.ore.total -= blueprint.obsidian.oreCost
                blueprint.clay.total -= blueprint.obsidian.clayCost
                blueprint.obsidian.many += 1
                if (excess[1] != 0) {
                    excess[1] -= 1
                }
            } else if (blueprint.ore.total >= blueprint.clay.oreCost || excess[0] != 0) {
                blueprint.ore.total -= blueprint.clay.oreCost
                blueprint.clay.many += 1
                if (excess[0] != 0) {
                    excess[0] -= 1
                }
            }
            println("${blueprint.ore.total} ${blueprint.clay.total} ${blueprint.obsidian.total} ${blueprint.geode.total}")
        }
    }
}