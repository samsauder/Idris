package com.idris.model.concepts

import com.idris.constants.Styles

class NewDay : Concept {
    override val icon = "D"
    val foundationNames: Array<String>
    val progressionNames: Array<String>

    constructor(name: String, skillName: String, description: String, foundationNames: Array<String>, progressionNames: Array<String>)
            : super(name, skillName, description) {
        this.foundationNames = foundationNames
        this.progressionNames = progressionNames
    }

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
        println(icon_name_skill())
    }

}