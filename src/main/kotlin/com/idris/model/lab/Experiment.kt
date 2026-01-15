package com.idris.model.lab
import com.idris.constants.Styles
import com.idris.database.operators.Creator.barc
import com.idris.model.concepts.Concept


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
        // TODO("Not yet implemented")
        println("\n$barc")
        println("NAME $name")
        println("\nSKILL $skillName")
        println("\nDESCRIPTION $description")
        var d = 1
        println("\nDAYS")
        for (dayName in segment) {
            if (dayName == "X") {
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





