package com.idris.system.concepts

import com.idris.system.extra.ConceptType
import com.idris.system.extra.EloRater
import com.idris.system.extra.Rater
import com.idris.system.extra.Util

// Soon to replace Challenge (cleaner)


class ChallengeNew : Objective {
    override val icon = "●"
    override val ct = ConceptType.CHALLENGE
    private var rating = 0.0
    private var rater: Rater

    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double,
                rater: Rater) : super(name, skillName, description, minutes) {
                    this.rater = rater
                }

    override fun update(won: Boolean) {
        rater.update(this, won)  // update the challenge rating for this ChallengeNew
        Util.printAttempt(name!!, won)        // output a visual of the result to stdout
    }

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        TODO("Not yet implemented")
    }
}

fun main() {
    val c = ChallengeNew("thing", "t", "", 1.0, EloRater)
    c.update(true)
}