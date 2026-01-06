package com.idris.model.newclasses

import com.idris.constants.Styles
import com.idris.model.Skill

// The Concept is the superclass of all Idris classes and stores common attributes:
// + name        |  a unique identifier
// + description |  a brief (< 200 char) synopsis

// EXAMPLE for a Challenge (a Concept subclass)
// name        :  puzzle2300
// description :  Complete a 2300-2399 rated Lichess puzzle within 1 minute.


abstract class Concept {
    var name: String
    var skillName: String
    var description: String

    constructor(
        name: String,
        skillName: String,
        description: String) {
        this.name = name
        this.skillName = skillName
        this.description = description
    }

    abstract val icon: String  // one character visual marker

    init {

    }

    abstract fun print()   // print a comprehensive representation
    abstract fun printL()  // print a one-line representation

    // Returns a string composed of this Concept's icon and its formatted name
    fun icon_name(): String {
        val fname = format(name, "", 20)  // formatted name
        return "${Styles.GREEN}[$icon]${Styles.RESET} $fname"
    }

    // Format a string to a specific style and pad it with whitespace to meet the specified width
    // * input "" for style and null for padding if not wanted
    // * style must be a string in Styles
    protected fun format(s: String, style: String?, width: Int?): String {
        val middle = if (width != null) s.padEnd(width, ' ') else s
        val fin = if (style != "") Styles.RESET else ""  // reset style at end if style was applied
        return "$style$middle$fin"
    }

}