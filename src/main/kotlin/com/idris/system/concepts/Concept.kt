package com.idris.system.concepts

import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styler.format
import com.idris.system.extra.Styler.pad
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles


abstract class Concept {
    abstract val icon: String  // one character visual marker
    abstract val ct: ConceptType
    var name: String? = null
    var skillName: String? = null
    var description: String? = null

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


    // Return true if every user-editable attribute is non-null for this Concept
    fun isComplete(): Boolean {
        // check common concept attributes
        if (this.name == null) return false
        if (this.skillName == null) return false
        if (this.description == null) return false

        if (ct == ConceptType.FOUNDATION ||
            ct == ConceptType.CHALLENGE ||
            ct == ConceptType.EXAM) {  // check common objective attributes
            val o = this as Objective
            if (o.minutes == null) return false
        }

        when (ct) {  // check unique attributes
            ConceptType.FOUNDATION -> {} // nothing extra needed
            ConceptType.CHALLENGE -> {
                val ch = this as Challenge
                if (ch.progressionName == null) return false
            }
            ConceptType.EXAM -> {TODO("implement for EXAM")}
            ConceptType.PROGRESSION -> {TODO("implement for PROGRESSION")}
            ConceptType.DAY -> {TODO("implement for DAY")}
            ConceptType.EXPERIMENT -> {TODO("implement for EXPERIMENT")}
            ConceptType.RECORD -> {TODO("implement for RECORD")}
            else -> { TODO("add tile eventually") }
        }

        return true
    }


    // Return a string composed of this Concept's icon, its formatted name, and its skill
    fun icon_name_skill(): String {
        val iconS = style("$icon", Styles.GREEN)               // icon (styled)
        val nameS = format(name!!, "", 20)           // name (styled)
        val skillS = format(skillName!!, Styles.BOLD, 20)  // skill (styled)
        return "$iconS  $nameS  $skillS"
    }

    fun conceptCore(labelWidth: Int) {
        print(pad("SKILL",labelWidth))
        println(style("  $skillName", Styles.BOLD))

        print(pad("DESCRIPTION",labelWidth))
        println("  $description")
    }
}