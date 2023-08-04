import java.io.File

fun solveA(input: List<String>): Int {
    var pairIndex = 0
    return (0..input.lastIndex step 3).map { i ->
        val list1 = buildList(input[i])
        val list2 = buildList(input[i + 1])
        pairIndex++
        pairIndex to compare(list1, list2)
    }.filter { it.second == -1 }.sumOf { it.first }
}

fun solveB(input: List<String>) = input.filter { it.isNotBlank() }
    .map { buildList(it) }
    .toMutableList()
    .also {
        it.add(buildList("[[2]]"))
        it.add(buildList("[[6]]"))
    }.sortedWith { o1, o2 -> compare(o1, o2) }
    .mapIndexed { index, anies -> index + 1 to anies }
    .filter { it.second == listOf(listOf(2)) || it.second == listOf(listOf(6)) }
    .fold(1) { acc, i -> acc * i.first }

private fun buildList(input: String): List<Any> {
    val stack = ArrayDeque<MutableList<Any>>()
    var currentList: MutableList<Any>? = null
    var string = input

    while (string.isNotEmpty()) {
        when {
            string.first() == '[' -> {
                currentList?.let { stack.addLast(it) }
                currentList = mutableListOf()
                string = string.drop(1)
            }

            string.first() == ']' -> {
                stack.removeLastOrNull()?.let { previousList ->
                    previousList.add(currentList as Any)
                    currentList = previousList
                }
                string = string.drop(1)
            }

            string.first() == ',' -> string = string.drop(1)

            else -> {
                "\\d+".toRegex().find(string)?.value?.toIntOrNull()?.let {
                    currentList?.add(it)
                    string = if (it > 9) string.drop(2) else string.drop(1)
                }
            }
        }
    }

    return currentList!!
}

private fun compare(left: Any, right: Any): Int {
    if (left is Int && right is Int) return left.compareTo(right)

    val leftIterator = (if (left is List<*>) left else listOf(left)).iterator()
    val rightIterator = (if (right is List<*>) right else listOf(right)).iterator()

    while (true) {
        val hasLeft = leftIterator.hasNext()
        val hasRight = rightIterator.hasNext()

        if (!hasLeft && !hasRight) return 0
        else if (!hasLeft) return -1
        else if (!hasRight) return 1
        else {
            val result = compare(checkNotNull(leftIterator.next()), checkNotNull(rightIterator.next()))
            if (result != 0) return result
        }
    }
}

fun main() {
    val input = File("day13/input.txt").readLines()
    println("Part One: ${solveA(input)}")
    println("Part Two: ${solveB(input)}")
}