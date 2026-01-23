package com.idris.system.extra

object Styler {
    // Format a string to a specific style and pad it with whitespace to meet the specified width
    // * input "" for style and null for padding if not wanted
    // * style must be a string in Styles
    fun format(s: String, style: String?, width: Int?): String {
        val middle = if (width != null) s.padEnd(width, ' ') else s
        val fin = if (style != "") Styles.RESET else ""  // reset style at end if style was applied
        return "$style$middle$fin"
    }

    // Format a string to a specific style
    fun style(s: String?, style: String?): String {
        val fin = if (style != "") Styles.RESET else ""  // reset style at end if style was applied
        return "$style$s$fin"
    }

    fun pad(s: String, width: Int): String {
        val middle = s.padEnd(width, ' ')
        return middle
    }
}