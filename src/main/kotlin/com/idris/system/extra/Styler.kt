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

    // Return a conditional ANSI color style depending on the given elo
    fun colorByElo(elo: Double): String {
        var style = ""
        val l0 = Styles.BLUE
        val l1 = Styles.CYAN
        val l2 = Styles.GREEN
        val l3 = Styles.YELLOW
        val l4 = Styles.RED

        if (elo in 1500.0..<1600.0) {
            style = l0
        } else if (elo in 1600.0..<1700.0) {
            style = l1
        } else if (elo in 1700.0..<1800.0) {
            style = l2
        } else if (elo in 1800.0..<1900.0) {
            style = l3
        } else if (elo >= 1900.0) {
            style = l4
        }
        return style
    }
}