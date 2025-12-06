package com.idris.model

import com.idris.model.auxiliary.Type

// model.NonDayPlan

class NonDay : Plan {
    constructor(id: String, type: Type, skill: Skill, subskills: List<Skill>?, startDate: String?) : super(id, type, skill, subskills, startDate)

    var subplans: MutableList<Plan> = mutableListOf()

    fun printNonDayPlan() {
        printPlan(0)
        println("    SUBPLANS")

        for (p in subplans) {
            p.printPlan(2)

            if (p.type == Type.day) {  // day plan components
                val dp = p as Day
                print("    ".repeat(3))
                println("OBJECTIVES")

                for (b in dp.objectives) {
                    b.printShort(4)
                }
            } else { // non day plans components
                val ndp = p as NonDay
                for (p2 in ndp.subplans) {
                    p2.printPlan(2)
                }
            }

            println()
        }
    }
}