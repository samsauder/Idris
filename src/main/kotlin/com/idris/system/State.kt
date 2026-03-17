package com.idris.system

import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles

// A snapshot in time of the user's physiological state


class State {
    private var level: String = ""  // H, M, or L with an optional + (and styled)

    private var sleep: Boolean = false
    private var food: Boolean = false
    private var water: Boolean = false
    private var cardio: Boolean = false
    private var supplements: HashMap<String, Boolean> = HashMap()  // any additional parameters

    private var load: Int = 0  // how many hours of high intensity tasks have been taken on today

    // val updated: String  // last time the state was updated

    constructor(digits: String) {
        val components = digits.split(" ")  // should be of the form xxxx y...

        // validate the components TODO
        // ...

        if (components[0][0] == '1') sleep = true
        if (components[0][1] == '1') food = true
        if (components[0][2] == '1') water = true
        if (components[0][3] == '1') cardio = true

        if (components[1].toInt() > 1) return  // return early if no supplements were provided

        val keys = supplements.keys.toList()

        for (i in 0..< components[1].length) {  // for each of the supplemental attributes
            if (components[1][i] == '1') supplements[keys[i]] = true  // if 1, set to true in the hashmap
        }

        level()  // generate the level
    }
    // ------------------------------------------------------------------------

    // Return the appropriate activity level (H, M, or L with an optional +) for the current state
    fun level(): String {
        if (level != "") return level  // return level if it already exists

        var allSupplements = true

        for (v in supplements.values) {  // for each supplement
            if (!v) {  // if this supplement has not been taken
                allSupplements = false  // then not all supplements haven been taken
                break
            }
        }

        val core: String = if (sleep && food && water && cardio) {  // if all core requirements are met
            if (load < 4) {  // if under 4 hours of high intensity activities have been done today
                "H"
            } else {
                "M"
            }
        } else {
            "L"
        }

        val mod: String = if (allSupplements) "+" else ""  // + or nothing

        val style: String? = when (core) {
            "H" -> Styles.RED
            "M" -> Styles.YELLOW
            "L" -> Styles.GREEN
            else -> null
        }

        level = style("$core$mod", style!!)
        return level  // return the styled activity level string
    }
}