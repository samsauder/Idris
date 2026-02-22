package com.idris.system.concepts

import com.idris.system.extra.Styles
import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Styler.style
import java.time.LocalDate


abstract class Objective : Concept {
    var minutes: Double = 0.0

    constructor(
        name: String,
        skillName: String,
        description: String,
        minutes: Double) : super(name, skillName, description) {
        this.minutes = minutes
    }

    constructor() : super()

    lateinit var objectiveType: ObjectiveType
    var symbol = ""
    val symbolColor = Styles.GREEN
    var symbolHolder = ""

    abstract fun update(won: Boolean)
}