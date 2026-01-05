package com.idris.model.newclasses

import com.idris.constants.Styles
import com.idris.model.Progression
import com.idris.model.objective.Foundation

// To replace Day eventually


class NewDay(override val name: String,
             override val description: String,
             val foundationNames: Array<String>,
             val progressionNames: Array<String>) : Concept() {

    fun printDay() {
        println("\n${Styles.GREEN}$name${Styles.RESET}")
        print("=======================================================================\n")
        for (fname in foundationNames) {
            if (fname == "X") break
            println(fname)
        }
        for (pname in progressionNames) {
            if (pname == "X") break
            println("${Styles.BOLD}$pname${Styles.RESET}")
        }
    }

}

fun main() {
    val nd = NewDay(
        "day",
        "a day",
        arrayOf("hang20mm90max", "pull85max"),
        arrayOf("V7inN", "V8inN"))
    nd.printDay()
}