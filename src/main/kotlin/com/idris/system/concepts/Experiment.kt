package com.idris.system.concepts

import com.idris.system.extra.Styler.pad
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import kotlin.properties.Delegates


class Experiment : Concept {
    override val icon = "\uD83D\uDDF2"
    lateinit var segment: List<String?>
    var segCount by Delegates.notNull<Int>()

    constructor()

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
        val labelWidth = 12  // width
        conceptCore(labelWidth)

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
            println(style("  $day", Styles.BLUE))
            d++
        }
        println()

        print(pad(" REPEAT",labelWidth))
        println("  x$segCount")
    }

    override fun printL() {
        println(icon_name_skill())
    }
}