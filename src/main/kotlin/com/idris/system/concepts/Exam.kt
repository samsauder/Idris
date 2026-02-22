package com.idris.system.concepts

import com.idris.system.extra.Styles
import com.idris.system.extra.ObjectiveType


class Exam : Objective {
    override val icon = "▶"
    var passed: Boolean = false

    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double) : super(name, skillName, description, minutes) {
        this.symbol = "▶"
        this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
        this.objectiveType = ObjectiveType.EXAM
    }


    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double,
                passed: Boolean) : this(name, skillName, description, minutes) {
        this.passed = passed
    }

    constructor()

    override fun update(won: Boolean) {
        passed = won
        println()
        Util.printAttempt(name, won)
    }

    override fun print() {
        conceptCore(12)
    }

    override fun printL() {
        val passedSym = if (passed) style("1", Styles.BOLD) else style("0", Styles.RED)
        println("${icon_name_skill()} ($passedSym)")
    }
}