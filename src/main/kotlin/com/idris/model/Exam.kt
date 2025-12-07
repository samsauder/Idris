package com.idris.model

import com.idris.constants.Styles.RESET
import com.idris.model.auxiliary.ObjectiveType

// model.Test

class Exam : Objective {
    var passed: Boolean = false


    constructor(
        name: String,
        skill: Skill?,
        description: String,
        minutes: Double
    ) : super(name, skill, description, minutes) {
        this.symbol = "▶"
        this.symbolHolder = "$symbolColor[$symbol]$RESET"
        this.objectiveType = ObjectiveType.EXAM
    }

    constructor(
        name: String,
        skill: Skill?,
        description: String,
        minutes: Double,
        passed: Boolean
    ) : this(name, skill, description, minutes) {
                this.passed = passed
                }


    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)  // indent level
        val passedStr: String = when (passed) {
            true -> {"✓"}
            false -> {""}
        }
        println("$lvl$symbolHolder $nameStr $skillStr $minsStr $descriptionStr $passedStr")
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