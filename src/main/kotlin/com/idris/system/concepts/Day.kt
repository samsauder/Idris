package com.idris.system.concepts

import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


class Day : Concept {
    override val icon = "⚙"
    var foundationNames: Array<String?> = emptyArray()
    var progressionNames: Array<String?> = emptyArray()

    constructor()

    constructor(
        name: String,
        skillName: String,
        description: String,
        foundationNames: Array<String?>,
        progressionNames: Array<String?>
    ) : super(name, skillName, description) {
        this.foundationNames = foundationNames
        this.progressionNames = progressionNames
    }

    override fun print() {
        conceptCore(12)
        println()
        transaction {
            for (fname in foundationNames) {
                // if (fname == "X") break  // if null  TODO remove comment
                if (fname == null) break
                val f = FOUNDATION.getOneNamed(fname)?.deEntify()  // get the Foundation with the given name
                f?.printL()
            }
            for (pname in progressionNames) {
                // if (pname == "X") break  // if null  TODO remove comment
                if (pname == null) break
                val p = PROGRESSION.getOneNamed(pname)?.deEntify()  // get the Progression with the given name
                p?.printL()
            }
        }
    }

    override fun printL() {
        println(icon_name_skill())
    }
}