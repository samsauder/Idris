package com.idris.system.concepts

import com.idris.system.extra.Styler.format
import com.idris.system.extra.Styler.pad
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import kotlin.properties.Delegates


class Record : Concept {
    override val icon = "R"
    lateinit var objectiveName: String       // name of completed f/c/e
    var won by Delegates.notNull<Boolean>()  // success/failure
    lateinit var date: String                // date completed

    constructor()

    constructor(name: String,
                skillName: String,
                description: String,
                objectiveName: String,
                won: Boolean,
                date: String) : super(name, skillName, description) {
        this.objectiveName = objectiveName
        this.won = won
        this.date = date
    }

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        println(icon_oname_skill_datetime())
    }

    fun icon_oname_skill_datetime(): String {
        val resultS = if (won) style("[+]", Styles.GREEN) else style("[-]", Styles.RED)
        val objNameS = pad(objectiveName, 20)
        val dateS = style(date, Styles.ITALIC)
        return "$resultS $objNameS ${format(skillName, Styles.BOLD, 14)} $dateS"
    }
}