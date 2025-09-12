package model

import constants.Styles
import model.auxiliary.ObjectiveType

// model.Test

class Test(id: String, skill: Skill, val description: String, minutes: Int) : Objective(id, skill, minutes) {
    override val objectiveType = ObjectiveType.TEST
    var passed: Boolean = false

    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)

        val passedText: String = when (passed) {
            true -> {"✓"}
            false -> {""}
        }

        println("$lvl${Styles.YELLOW}[TEST ▶]${Styles.RESET} ${Styles.BOLD}${skill.id}${Styles.RESET} ${Styles.GREEN}${minutes}m${Styles.RESET} $id $passedText")
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