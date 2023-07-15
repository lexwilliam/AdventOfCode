package day8

import java.io.File

fun checkIfTreeIsVisible(treeMap: List<List<Int>>, i: Int, j: Int): Boolean {
    val tree = treeMap[i][j]
    var count = 0
    // Up
    for (index in 0 until i) {
        if (tree <= treeMap[index][j]) {
            count++
            break
        }
    }
    // Down
    for (index in i+1 until treeMap[0].size) {
        if (tree <= treeMap[index][j]) {
            count++
            break
        }
    }
    // Left
    for (index in 0 until j) {
        if (tree <= treeMap[i][index]) {
            count++
            break
        }
    }
    // Right
    for (index in j+1 until treeMap.size) {
        if (tree <= treeMap[i][index]) {
            count++
            break
        }
    }
    return count != 4
}

fun main() {
    val inputs = File("day8/input.txt").readLines()
    val treeMap = inputs.map { line -> line.map { it.digitToInt() } }
    val aroundTheEdgeTotal = treeMap.size * 2 + treeMap[0].size * 2 - 4
    var interiorTotal = 0
    for (i in 1 until treeMap.size-1) {
        for (j in 1 until treeMap[i].size-1) {
            if (checkIfTreeIsVisible(treeMap, i, j)) {
                interiorTotal++
            }
        }
    }
    println("${aroundTheEdgeTotal + interiorTotal} trees visible outside the grid")
}