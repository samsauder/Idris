package model

import model.auxiliary.ObjectiveType
import java.math.BigDecimal

// model.Objective

abstract class Objective (val name: String, val skill: Skill, val description: String, val minutes: Double)  {
    abstract val objectiveType: ObjectiveType
    abstract fun printShort(startLevel: Int)  // print a one line text representation of the objective
    abstract fun log(value: Double)
}