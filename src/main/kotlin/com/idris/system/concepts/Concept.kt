package com.idris.system.concepts

import com.idris.system.extra.Styler.format
import com.idris.system.extra.Styler.pad
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles


abstract class Concept {
    abstract val icon: String  // one character visual marker
    lateinit var name: String
    lateinit var skillName: String
    lateinit var description: String

    constructor(name: String,
                skillName: String,
                description: String) {
        this.name = name
        this.skillName = skillName
        this.description = description
    }

    constructor()

    abstract fun print()   // print a comprehensive representation
    abstract fun printL()  // print a one-line representation

    // Return a string composed of this Concept's icon, its formatted name, and its skill
    fun icon_name_skill(): String {
        val iconf = "${Styles.GREEN}[$icon]${Styles.RESET}"
        val fname = format(name, "", 20)  // formatted name
        val skillf = format(skillName, Styles.BOLD, 14)

        return "$iconf  $fname | $skillf"
    }

    fun conceptCore(labelWidth: Int) {
        // print(pad(" NAME", labelWidth))
        // println("  $name")

        print(pad(" SKILL",labelWidth))
        println(style("  $skillName", Styles.BOLD))

        print(pad(" DESCRIPTION",labelWidth))
        println("  $description")
    }
}