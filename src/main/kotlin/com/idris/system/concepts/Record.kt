package com.idris.system.concepts

import com.idris.system.extra.Styler.format
import com.idris.system.extra.Styler.pad
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles


class Record : Concept {
    override val icon = "R"
    var objectiveName: String       // name of completed f/c/e
    var won: Boolean                // success/failure
    var date: String                // date completed

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
        val resultf = if (won) style("[+]", Styles.GREEN) else style("[-]", Styles.RED)
        val onamef = pad(objectiveName, 20)
        val datef = style(date, Styles.ITALIC)
        return "$resultf $onamef ${format(skillName, Styles.BOLD, 14)} $datef"
    }

}