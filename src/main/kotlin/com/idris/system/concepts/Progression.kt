package com.idris.system.concepts

import com.idris.database.entities.CHALLENGES
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styler
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Util.challengesFromNames
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


class Progression : Concept {
    override var icon = "➤"
    override var ct = ConceptType.PROGRESSION
    var challengeNames: List<String>? = null
    lateinit var challenges: Array<Challenge?>

    constructor()

    constructor(name: String,
                skillName: String,
                description: String,
                challengeNames: List<String>) : super(name, skillName, description) {
        this.challengeNames = challengeNames
        challenges = arrayOfNulls(challengeNames.size)

        transaction {
            challenges = challengesFromNames(challengeNames)
        }
    }


    // Log the result for the specified Challenge then:
    // + log the result of all lower level ones (may be win or loss)
    // + log a loss on all higher level ones
    fun massLog(targetChallenge: String, result: Double) {
        var seenIndex = -1  // index the target was seen at
        var i = 0

        for (challenge in challenges) {
            if (challenge == null) continue
            println()
            if (seenIndex == -1) {
                if (challenge.name == targetChallenge) {
                    seenIndex = i
                }
            }

            if (i <= seenIndex || seenIndex == -1) {
                CHALLENGES.update(challenge, result)  // log result on each challenge up to and including targetChallenge
            } else {
                CHALLENGES.update(challenge, 0.0)  // log loss on each challenge higher than targetChallenge
            }

            i++
        }


    }

    override fun print() {
        conceptCore(12)
    }

    fun printSeq() {  // print the sequence of challenges for the current progression
        var i = 0
        val nonNullCt = challenges.filterNotNull().count()

        val l = "["
        val r = "]"

        print("$l ")

        var unit: String? = null

        for (challenge in challenges) {
            if (challenge == null) break

            val constraint = challenge.name!!.substringAfter(".")
            val value = constraint.substring(0, constraint.length - 1)                     // get constraint value
            unit = constraint.substring(constraint.length - 1, constraint.length)  // get the constraint unit

            val styledConstraint = style("($value$unit)", Styler.colorByChallenge(challenge))  // a number and a unit (like 3m)

            print(styledConstraint)

            if (i == nonNullCt - 1) break
            print(" --> ")

            i++
        }
        print(" $r")
    }

    // prints the activity, the tier, and then the sequence
    override fun printL() {
        val i = icon()
        val at = name!!.substringBefore(".")  // activity and tier (eg. Mixed and 1900)
        val s = skill()

        print("$i  $at::")
        printSeq()
        println()
    }
}