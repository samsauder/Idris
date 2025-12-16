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
        print("${Styles.ITALIC}$name${Styles.RESET}  [")
        var f = 0;
        for (fname in foundationNames) {
            if (fname == "X") continue
            if (f != 0) print(" : ")
            print(fname)
            f++;
        }
        print("] [")

        var p = 0
        for (pname in progressionNames) {
            if (pname == "X") continue
            if (p != 0) print(" : ")
            print("${Styles.BOLD}$pname${Styles.RESET}")
            p++
        }
        print("]\n")
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