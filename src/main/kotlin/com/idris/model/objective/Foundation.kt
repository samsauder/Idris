package com.idris.model.objective

import com.idris.constants.Styles
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType

class Foundation : Objective {
    var done: Boolean = false


    constructor(name: String,
                skill: Skill?,
                description: String,
                minutes: Double) : super(name, skill, description, minutes) {
        this.symbol = "◯"
        this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
        this.objectiveType = ObjectiveType.FOUNDATION
    }


    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)                                          // indent level
        println("$lvl$symbolHolder $nameStr $skillStr $minsStr $descriptionStr")
    }


    // 1.0 = done, -1 = error
    override fun log(value: Double) {
        when(value) {
            1.0 -> { done = true }
            else -> {}
        }
    }

}