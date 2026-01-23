package com.idris.system.concepts

import com.idris.database.operators.todo.Creator.bar
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
        val barChar = "-"
        val barWidth = 60
        val labelWidth = 12  // width

        println("\n${bar(barChar, barWidth)}")
        print(pad(" NAME", labelWidth))
        println(style("  $name", Styles.BOLD))
        //println()

        print(pad(" SKILL",labelWidth))
        println(style("  $skillName", Styles.ITALIC))
        //println()

        print(pad(" DESCRIPTION",labelWidth))
        println(style("  $description", Styles.YELLOW))
        println()

        print(pad(" WEEK", labelWidth))
        var d = 0
        for (day in segment) {
            if (day == "X") continue  // empty day slot
            if (d != 0) print(pad("", labelWidth))
            print("  |")
            if (day == "_") {  // rest day slot
                println()
                continue
            }
            println(style("  $day", Styles.GREEN))
            d++
        }
        println()

        print(pad(" REPEAT",labelWidth))
        println("  x$segCount")
        println(bar(barChar, barWidth))
    }


    override fun printL() {
        println(icon_name_skill())
    }

    // fun begin()
}