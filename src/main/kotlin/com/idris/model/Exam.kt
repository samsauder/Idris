package com.idris.model

import com.idris.constants.Styles
import com.idris.model.auxiliary.ObjectiveType

// model.Test

class Exam(id: String, skill: Skill, description: String, minutes: Double) : Objective(id, skill, description, minutes) {
    override val objectiveType = ObjectiveType.TEST
    var passed: Boolean = false

    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)

        val passedText: String = when (passed) {
            true -> {"✓"}
            false -> {""}
        }

        println("$lvl${Styles.YELLOW}[▶]${Styles.RESET} ${Styles.BOLD}${skill.id}${Styles.RESET} ${Styles.GREEN}${minutes}m${Styles.RESET} $name $passedText")
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