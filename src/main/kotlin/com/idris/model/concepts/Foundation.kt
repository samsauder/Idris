package com.idris.model.concepts

import com.idris.constants.Styles
import com.idris.model.enums.ObjectiveType


class Foundation : Objective {
    override val icon = "◯"

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        println(icon_name_skill())
    }

    var done: Boolean = false


    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double) : super(name, skillName, description, minutes) {
        this.symbol = "◯"
        this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
        this.objectiveType = ObjectiveType.FOUNDATION
    }

    // 1.0 = done, -1 = error
    override fun log(value: Double) {
        when(value) {
            1.0 -> { done = true }
            else -> {}
        }
    }

}