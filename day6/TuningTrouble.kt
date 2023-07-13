package day6

fun main() {
    val input = readln()
    for (i in 0..input.length-4) {
        val subString = input.subSequence(i, i+4)
        if(subString.length == subString.toList().distinct().count()) {
            print("First marker after character ${i+4}")
            break
        }
    }
}