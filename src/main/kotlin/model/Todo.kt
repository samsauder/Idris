package model

import constants.Styles
import model.auxiliary.ObjectiveType

// model.Task

class Todo(id: String, skill: Skill, description: String, minutes: Double) : Objective(id, skill, description, minutes) {
    override val objectiveType = ObjectiveType.TODO

    var done: Boolean = false

    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)

        println("$lvl${Styles.YELLOW}[TODO ◯]${Styles.RESET} ${Styles.BOLD}${skill.id}${Styles.RESET} ${Styles.GREEN}${minutes}m${Styles.RESET} $name")
    }

    // 1.0 = done, -1 = error
    override fun log(value: Double) {
        when(value) {
            1.0 -> {
                done = true
            }

            else -> {}
        }
    }
}