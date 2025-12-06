package com.idris.model

import com.idris.constants.Styles
import com.idris.model.auxiliary.ObjectiveType


abstract class Objective(var name: String,
                         var skill: Skill?,
                         var description: String,
                         var minutes: Double) {
    lateinit var objectiveType: ObjectiveType
    var symbol = ""
    val symbolColor = Styles.YELLOW
    var symbolHolder = ""
    var nameStr = ""
    var skillStr = ""
    var descriptionStr = ""
    var minsStr = ""


    init {
        nameStr = pad(name,  18)                                                  // name + style + padding
        var skillName = ""
        skill?.let { skillName = it.id };
        skillStr = styleAndPad(skillName, Styles.BOLD, 10)  // skill + style + padding
        descriptionStr = style(description, Styles.CYAN)
        minsStr = styleAndPad("${minutes.toInt()}m", Styles.ITALIC, 5)            // mins + style + padding
    }


    abstract fun printShort(startLevel: Int)  // print a one line text representation of the objective

    abstract fun log(value: Double)


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

}