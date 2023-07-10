package day4

fun main() {
    var count = 0
    repeat(5) {
        val input = readln().split(",")
        val a = input[0].split("-")
        val b = input[1].split("-")
        var strA = ""
        var strB = ""


        for (i in a[0].toInt()..a[1].toInt()) {
            strA += i.toString()
        }
        for (i in b[0].toInt()..b[1].toInt()) {
            strB += i.toString()
        }

        if (strA.contains(strB) || strB.contains(strA)) {
            count += 1
        }
    }
    println("There are $count assignment pairs that are one range fully contain the other")
}