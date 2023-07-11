package day5
fun main() {
    val inputList = mutableListOf<List<String>>()
    repeat(3) {
        print("Input no.$it stack: ")
        val input = readln().split(" ")
        inputList.add(input)
    }
    val stackList = inputList.map { input -> ArrayDeque(input) }

    repeat(4) {
        val command = readln().filter { it.isDigit() }.map { it.digitToInt() }
        repeat(command[0]) {
            val crate = stackList[command[1]-1].removeLast()
            stackList[command[2]-1].addLast(crate)
        }
    }
    print("${stackList[0].removeLast()}, ${stackList[1].removeLast()}, ${stackList[2].removeLast()}")
}