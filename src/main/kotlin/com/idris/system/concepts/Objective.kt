package com.idris.system.concepts

import com.idris.system.extra.ObjectiveType


abstract class Objective : Concept {
    lateinit var objectiveType: ObjectiveType
    var minutes: Double? = null

    constructor(
        name: String,
        skillName: String,
        description: String,
        minutes: Double) : super(name, skillName, description) {
        this.minutes = minutes
    }

    constructor() : super()


    abstract fun update(won: Boolean)
}