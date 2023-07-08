package day3

fun main() {
    val rucksacks = mutableListOf<String>()
    repeat(6) {
        print("Enter the contents of rucksack ${it + 1}: ")
        val contents = readlnOrNull() ?: ""
        rucksacks.add(contents)
    }

    val priorities = mutableSetOf<Int>()

    for (rucksack in rucksacks) {
        val compartments = splitRucksack(rucksack)
        val commonItems = findCommonItems(compartments)
        priorities.addAll(getPriorities(commonItems))
    }

    val sumOfPriorities = priorities.sum()

    println("Sum of priorities: $sumOfPriorities")
}

fun splitRucksack(rucksack: String): List<String> {
    val compartments = mutableListOf<String>()
    var i = 0

    while (i < rucksack.length) {
        val compartment = StringBuilder()

        while (i < rucksack.length && !rucksack[i].isLetter()) {
            i++
        }

        while (i < rucksack.length && rucksack[i].isLetter()) {
            compartment.append(rucksack[i])
            i++
        }

        if (compartment.isNotEmpty()) {
            compartments.add(compartment.toString())
        }
    }

    return compartments
}

fun findCommonItems(compartments: List<String>): List<Char> {
    val commonItems = mutableListOf<Char>()

    if (compartments.isNotEmpty()) {
        val firstCompartmentItems = compartments[0].toSet()

        for (item in firstCompartmentItems) {
            var isCommon = true

            for (i in 1 until compartments.size) {
                if (!compartments[i].contains(item)) {
                    isCommon = false
                    break
                }
            }

            if (isCommon) {
                commonItems.add(item)
            }
        }
    }

    return commonItems
}

fun getPriorities(items: List<Char>): List<Int> {
    val priorities = mutableListOf<Int>()

    for (item in items) {
        val priority = if (item.isLowerCase()) {
            item - 'a' + 1
        } else {
            item - 'A' + 27
        }

        priorities.add(priority)
    }

    return priorities
}