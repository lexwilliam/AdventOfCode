package day1

fun main() {
    var list: Array<Int> = arrayOf()
    for (i in 0..4) {
        var j = 0
        var count = 0
        while (true) {
            val input = readln()
            if (input == "") {
                break
            }
            count += input.toInt()
            list += count
            j++
        }
    }
    print("Elf No.${list.indices.maxBy { list[it] } + 1} has the most calories")
}