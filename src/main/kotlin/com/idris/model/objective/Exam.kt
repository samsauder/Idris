package com.idris.model.objective

import com.idris.constants.Styles
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType


class Exam : Objective {
    override val icon = "▶"

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        TODO("Not yet implemented")
    }

    var passed: Boolean = false


    constructor(
        name: String,
        skillName: String,
        description: String,
        minutes: Double
    ) : super(name, skillName, description, minutes) {
        this.symbol = "▶"
        this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
        this.objectiveType = ObjectiveType.EXAM
    }


    constructor(
        name: String,
        skillName: String,
        description: String,
        minutes: Double,
        passed: Boolean
    ) : this(name, skillName, description, minutes) {
        this.passed = passed
    }


    override fun log(value: Double) {
        when (value) {
            1.0 -> {
                passed = true
            }
            0.0 -> {}
        }
    }

}