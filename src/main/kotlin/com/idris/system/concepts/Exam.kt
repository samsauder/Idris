package com.idris.system.concepts

import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styles
import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Util


class Exam : Objective {
    override val icon = "▼"
    override val ct = ConceptType.EXAM
    var passed: Boolean = false

    constructor()

    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double) : super(name, skillName, description, minutes) {
        this.objectiveType = ObjectiveType.EXAM
    }

    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double,
                passed: Boolean) : this(name, skillName, description, minutes) {
        this.passed = passed
    }

    override fun update(won: Boolean) {
        passed = won
        println()
        Util.printAttempt(name!!, won)
    }

    override fun print() {
        conceptCore(12)
    }

    override fun printL() {
        val passedSym = if (passed) style("1", Styles.BOLD) else style("0", Styles.RED)
        println("${icon_name_skill()} ($passedSym)")
    }
}