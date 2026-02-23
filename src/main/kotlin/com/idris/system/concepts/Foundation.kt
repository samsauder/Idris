package com.idris.system.concepts

import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Util


class Foundation : Objective {
    override val icon = "◯"

    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double) : super(name, skillName, description, minutes) {
        this.objectiveType = ObjectiveType.FOUNDATION
    }

    // 1.0 = done, -1 = error
    override fun update(won: Boolean) {
        println()
        Util.printAttempt(name, true)
    }

    override fun print() {
        conceptCore(12)
    }

    override fun printL() {
        println(icon_name_skill())
    }
}