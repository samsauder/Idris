package com.idris.system.concepts

import com.idris.system.extra.Styles


class Experiment : Concept {
    override val icon = "X"
    var segment: List<String?>
    var segCount: Int
    // startDate
    // adherence

    constructor(
        name: String,
        skillName: String,
        description: String,
        segment: List<String?>,
        segCount: Int) : super(name, skillName, description) {
        this.segment = segment
        this.segCount = segCount
        }

    override fun print() {
        println("NAME $name")
        println("\nSKILL $skillName")
        println("\nDESCRIPTION $description")
        var d = 1
        println("\nDAYS")
        for (dayName in segment) {
            if (dayName == ".") {  // if rest day
                println("  D${d}")
                continue
            }
            println("  D${d} ${Styles.BOLD}$dayName${Styles.RESET}")
            d++
        }
        println("\nREPEAT x${segCount}")
    }

    override fun printL() {
        println(icon_name_skill())
    }

    // fun begin()
}