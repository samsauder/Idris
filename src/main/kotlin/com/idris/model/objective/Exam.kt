package com.idris.model.objective

import com.idris.constants.Styles
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType


class Exam : Objective {
    override val icon = "▶"
    override var skillName: String = ""

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
    /*
    constructor(
        name: String,
        skill: Skill?,
        description: String,
        minutes: Double
    ) : super(name, skill, description, minutes) {
        this.symbol = "▶"
        this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
        this.objectiveType = ObjectiveType.EXAM
    }*/

    constructor(
        name: String,
        skillName: String,
        description: String,
        minutes: Double,
        passed: Boolean
    ) : this(name, skillName, description, minutes) {
        this.passed = passed
    }

    /*
    constructor(
        name: String,
        skill: Skill?,
        description: String,
        minutes: Double,
        passed: Boolean
    ) : this(name, skill, description, minutes) {
        this.passed = passed
    }*/


    /*
    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)  // indent level
        val passedStr: String = when (passed) {
            true -> {"✓"}
            false -> {""}
        }
        println("$lvl$symbolHolder $nameStr $skillStr $minsStr $descriptionStr $passedStr")
    }
     */


    override fun log(value: Double) {
        when (value) {
            1.0 -> {
                passed = true
            }
            0.0 -> {}
        }
    }

}