package com.idris.model.objective

import com.idris.constants.Styles
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType
import com.idris.model.newclasses.Concept


abstract class Objective : Concept {
    var minutes: Double

    constructor(
        name: String,
        skillName: String,
        description: String,
        minutes: Double) : super(name, skillName, description) {
        // println("      '$skillName' skill passed to Objective constructor")
        this.minutes = minutes
    }

    lateinit var objectiveType: ObjectiveType
    var symbol = ""
    val symbolColor = Styles.GREEN
    var symbolHolder = ""

    // var nameStr = ""
    // var skillStr = ""
    // var descriptionStr = ""
    // var minsStr = ""

    init {
        /*
        // nameStr = styleAndPad(name, Styles.BOLD,  20)  // name + style + padding
        // nameStr = styleAndPad(name, Styles.GREEN,  20)  // name + style + padding
        nameStr = pad(name,  20)
        var skillName = ""
        skill?.let { skillName = it.id };
        // skillStr = pad(skillName,  16)
        skillStr = styleAndPad(skillName, Styles.ITALIC, 14)
        // descriptionStr = description  // uncomment if wanted
        descriptionStr = style(description, Styles.YELLOW)
        // minsStr = styleAndPad("${minutes.toInt()}m", Styles.ITALIC, 5)            // mins + style + padding
        minsStr = pad("${minutes.toInt()}m",  5)
        */
    }

    // Return a string composed of this Objective's icon, its formatted name, and its skill
    fun icon_name_skill(): String {
        val skillf = format(skillName, "", 14)
        return "${icon_name()} | $skillf"
    }

    // abstract fun printShort(startLevel: Int)  // print a one line text representation of the objective

    abstract fun log(value: Double)
}