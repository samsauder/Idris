package com.idris.model.concepts

import com.idris.constants.Styles
import com.idris.model.enums.ObjectiveType


abstract class Objective : Concept {
    var minutes: Double

    constructor(
        name: String,
        skillName: String,
        description: String,
        minutes: Double) : super(name, skillName, description) {
        this.minutes = minutes
    }

    lateinit var objectiveType: ObjectiveType
    var symbol = ""
    val symbolColor = Styles.GREEN
    var symbolHolder = ""

    abstract fun log(value: Double)
}