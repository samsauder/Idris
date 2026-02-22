package com.idris.system.extra

import com.idris.system.concepts.Challenge

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

    
    // Return a style depending on the elo of the given challenge
    fun colorByChallenge(challenge: Challenge): String {
        val elo = challenge.challengeElo
        var style = ""
        val l0 = Styles.BLUE
        val l1 = Styles.GREEN
        val l2 = Styles.YELLOW
        val l3 = Styles.RED

        if (challenge.attempts == 0) {  // no styling
            style = Styles.BLACK
        } else if (elo in 1500.0..<1600.0) {
            style = l0
        } else if (elo in 1600.0..<1700.0) {
            style = l1
        } else if (elo in 1700.0..<1800.0) {
            style = l2
        } else if (elo >= 1800.0) {
            style = l3
        }
        return style
    }
}