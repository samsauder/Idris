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

    override fun log(value: Double) {
        when (value) {
            1.0 -> {
                passed = true
            }
            0.0 -> {}
        }
    }

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        println(icon_name_skill())
    }
}