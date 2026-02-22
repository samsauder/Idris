package com.idris.system.concepts

import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Util


class Foundation : Objective {
    override val icon = "◯"
    // var done: Boolean = false

    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double) : super(name, skillName, description, minutes) {
        this.symbol = "◯"
        // this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
        this.objectiveType = ObjectiveType.FOUNDATION
    }

    constructor() : super()

    // 1.0 = done, -1 = error
    override fun update(won: Boolean) {
        println()
        Util.printAttempt(name, true)
    }

    override fun print() {
        val labelWidth = 12  // width
        conceptCore(12)
    }

    override fun printL() {
        println(icon_name_skill())
    }
}