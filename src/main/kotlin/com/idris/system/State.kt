package com.idris.system

import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles

// A snapshot in time of the user's physiological state


class State {
    private var level: String = ""  // H, M, or L with an optional + (and styled)

    var sleep: Boolean = false
    var food: Boolean = false
    var water: Boolean = false
    var cardio: Boolean = false

    private var supplementCt = 4
    var supplements: Array<Boolean?> = arrayOfNulls(supplementCt)  // any additional parameters

    var load: Int = 0  // how many hours of high intensity tasks have been taken on today

    // val updated: String  // last time the state was updated

    // precondition: digits is valid
    constructor(digits: String) {
        val components = digits.split(" ")  // should be of the form xxxx y...

        // Validate the core
        if (components[0].length != 4) return

        if (components[0][0] == '1') sleep = true
        if (components[0][1] == '1') food = true
        if (components[0][2] == '1') water = true
        if (components[0][3] == '1') cardio = true

        if (components.size == 1) return                 // if no

        // Validate the supplements
        if (components[1].length < supplementCt) return  // if incorrect number of supplements

        for (i in 0..< components[1].length) {  // for each of the supplemental attributes
            if (components[1][i] == '1') supplements[i] = true else supplements[i] = false  // if 1, set to true in the hashmap
        }

        level()  // generate the level
    }
    // ------------------------------------------------------------------------

    // Return the appropriate activity level (H, M, or L with an optional +) for the current state
    fun level(): String {
        if (level != "") return level  // return level if it already exists

        var allSupplements = true

        for (taken in supplements) {  // for each supplement
            if (taken == null || !taken) {  // if this supplement has not been taken
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

        return "$core$mod"
    }
}