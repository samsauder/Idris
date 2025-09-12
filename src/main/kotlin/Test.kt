// Test

import StyleConstants.GREEN
import StyleConstants.YELLOW
import StyleConstants.RED
import StyleConstants.BLUE
import StyleConstants.BOLD
import StyleConstants.RESET
import StyleConstants.ITALIC

class Test(id: String, skill: Skill, val description: String, minutes: Int) : Objective(id, skill, minutes) {
    override val objectiveType = ObjectiveType.TEST
    var passed: Boolean = false

    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)

        val passedText: String = when (passed) {
            true -> {"✓"}
            false -> {""}
        }


        println("$lvl$YELLOW[TEST ▶]$RESET $BOLD${skill.id}$RESET $GREEN${minutes}m$RESET $id $passedText")
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