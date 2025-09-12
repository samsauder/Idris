// Task

import StyleConstants.GREEN
import StyleConstants.YELLOW
import StyleConstants.RED
import StyleConstants.BLUE
import StyleConstants.BOLD
import StyleConstants.RESET

class Task(id: String, skill: Skill, val description: String, minutes: Int) : Objective(id, skill, minutes) {
    override val objectiveType = ObjectiveType.TASK

    var done: Boolean = false
    // var error: Boolean = false

    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)

        println("$lvl$YELLOW[TASK ◯]$RESET $BOLD${skill.id}$RESET $GREEN${minutes}m$RESET $id")
    }

    // 1.0 = done, -1 = error
    override fun log(value: Double) {
        when(value) {
            1.0 -> {
                done = true
            }
            /*-1.0 -> {
                error = true
            }*/
            else -> {}
        }
    }
}