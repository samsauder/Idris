package model

import model.auxiliary.ObjectiveType

// model.Objective

abstract class Objective (val id: String, val skill: Skill, val minutes: Int)  {
    abstract val objectiveType: ObjectiveType
    abstract fun printShort(startLevel: Int)  // print a one line text representation of the objective
    abstract fun log(value: Double)
}