package day2

fun myScore(me: Int, opponent: Int): Int {
    if (me == opponent) {
        return 3
    }
    when (me) {
        1 -> {
            return if (opponent == 2) 0
            else 6
        }
        2 -> {
            return if (opponent == 3) 0
            else 6
        }
        3 -> {
            return if (opponent == 1) 0
            else 6
        }
    }
    return 0
}
fun main() {
    var totalScore = 0
    for (i in 0..2) {
        var me = 0
        var opponent = 0
        val input = readln().split(" ")
        when(input[0]) {
            "A" -> opponent = 1
            "B" -> opponent = 2
            "C" -> opponent = 3
        }
        when(input[1]) {
            "X" -> me = 1
            "Y" -> me = 2
            "Z" -> me = 3
        }
        totalScore += myScore(me, opponent) + me
    }
    print("Total Score: $totalScore")
}