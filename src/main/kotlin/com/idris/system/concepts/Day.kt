package com.idris.system.concepts

import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


class Day : Concept {
    override val icon = "D"
    val foundationNames: Array<String>
    val progressionNames: Array<String>

    constructor(name: String,
                skillName: String,
                description: String,
                foundationNames: Array<String>,
                progressionNames: Array<String>) : super(name, skillName, description) {
        this.foundationNames = foundationNames
        this.progressionNames = progressionNames
    }

    override fun print() {
        println(style("$description\n", Styles.YELLOW))
        transaction {
            for (fname in foundationNames) {
                if (fname == "X") break  // if null
                val f = FOUNDATION.getOneNamed(fname)?.deEntify()  // get the Foundation with the given name
                f?.printL()
            }
            for (pname in progressionNames) {
                if (pname == "X") break  // if null
                val p = PROGRESSION.getOneNamed(pname)?.deEntify()  // get the Progression with the given name
                p?.printL()
            }
        }
    }

    override fun printL() {
        println(icon_name_skill())
    }
}