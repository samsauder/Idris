package com.idris.model

import com.idris.model.auxiliary.Type

// model.DayPlan

class Day(id: String, skill: Skill, subskills: List<Skill>?, startDate: String?) : Plan(id, Type.day, skill, subskills, startDate) {
    var objectives: MutableList<Objective> = mutableListOf()
        // get() = field
        set(value) {
            field = value
        }

    fun printDayPlan() {
        printPlan(0)
        println("    OBJECTIVES")

        for (obj in objectives) {
            obj.printShort(2)
        }
    }
}