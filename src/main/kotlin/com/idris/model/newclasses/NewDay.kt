package com.idris.model.newclasses

import com.idris.constants.Styles
import com.idris.model.Progression
import com.idris.model.objective.Foundation

// To replace Day eventually


class NewDay(override val name: String,
             override val skillName: String,
             override val description: String,
             val foundationNames: Array<String>,
             val progressionNames: Array<String>) : Concept() {
    override val icon = "D"

    override fun print() {
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

    override fun printL() {
        println(icon_name())
    }

}