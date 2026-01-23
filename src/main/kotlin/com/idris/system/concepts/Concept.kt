package com.idris.system.concepts

import com.idris.system.extra.Styles


abstract class Concept {
    abstract val icon: String  // one character visual marker
    var name: String
    var skillName: String
    var description: String

    constructor(name: String,
                skillName: String,
                description: String) {
        this.name = name
        this.skillName = skillName
        this.description = description
    }

    abstract fun print()   // print a comprehensive representation
    abstract fun printL()  // print a one-line representation

    // Return a string composed of this Concept's icon, its formatted name, and its skill
    fun icon_name_skill(): String {
        val iconf = "${Styles.GREEN}[$icon]${Styles.RESET}"
        val fname = format(name, "", 20)  // formatted name
        val skillf = format(skillName, Styles.BOLD, 14)

        return "$iconf $fname | $skillf"
    }

    // Format a string to a specific style and pad it with whitespace to meet the specified width
    // * input "" for style and null for padding if not wanted
    // * style must be a string in Styles
    protected fun format(s: String, style: String?, width: Int?): String {
        val middle = if (width != null) s.padEnd(width, ' ') else s
        val fin = if (style != "") Styles.RESET else ""  // reset style at end if style was applied
        return "$style$middle$fin"
    }

    // Format a string to a specific style
    protected fun style(s: String, style: String?): String {
        val fin = if (style != "") Styles.RESET else ""  // reset style at end if style was applied
        return "$style$s$fin"
    }
}