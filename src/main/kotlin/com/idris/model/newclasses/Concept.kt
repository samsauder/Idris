package com.idris.model.newclasses

import com.idris.constants.Styles

// The Concept is the superclass of all Idris classes and stores common attributes:
// + name        |  a unique identifier
// + description |  a brief (< 200 char) synopsis

// EXAMPLE for a Challenge (a Concept subclass)
// name        :  puzzle2300
// description :  Complete a 2300-2399 rated Lichess puzzle within 1 minute.


abstract class Concept {
    abstract val name: String
    abstract val description: String
    abstract val icon: String  // one character visual marker

    init {
        // nameStr = pad(name,  20)
    }

    abstract fun print()   // print a comprehensive representation
    abstract fun printL()  // print a one-line representation

    /*
    protected fun style(s: String, style: String) : String{
        return "$style$s${Styles.RESET}"
    }

    // Returns a whitespace-padded version of the given string
    protected fun pad(s: String, padLen: Int) : String {
        return s.padEnd(padLen, ' ')
    }

    // Returns an ansi-styled and whitespace-padded version of the given string
    protected fun styleAndPad(s: String, style: String?, padLen: Int) : String {
        return "$style${s.padEnd(padLen, ' ')}${Styles.RESET}"
    }
    */

    // Returns a string composed of this Concept's icon and its formatted name
    fun icon_name(): String {
        val fname = format(name, null, 20)  // formatted name
        return "[$icon] $fname"
    }

    // Format a string to a specific style and pad it with whitespace to meet the specified width
    // * input null for style and/or padding if desired
    // * style must be a string in Styles
    protected fun format(s: String, style: String?, width: Int?): String {
        val middle = if (width != null) s.padEnd(width, ' ') else s
        val fin = if (style != null) Styles.RESET else ""  // reset style at end if style was applied
        return "$style$middle$fin"
    }

}