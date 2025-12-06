package com.idris.model

import com.idris.model.auxiliary.ObjectiveType

// com.idris.Objective

abstract class Objective (val name: String, val skill: Skill, val description: String, val minutes: Double)  {
    abstract val objectiveType: ObjectiveType
    abstract fun printShort(startLevel: Int)  // print a one line text representation of the objective
    abstract fun log(value: Double)
}