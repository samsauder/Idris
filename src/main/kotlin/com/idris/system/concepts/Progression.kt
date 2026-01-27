package com.idris.system.concepts
import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.operators.todo.Logger
import com.idris.system.extra.Styler
import com.idris.system.extra.Styler.format
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


class Progression : Concept {
    override var icon = "P"
    lateinit var challengeNames: List<String>
    lateinit var challenges: Array<Challenge?>

    constructor(name: String,
                skillName: String,
                description: String,
                challengeNames: List<String>) : super(name, skillName, description) {

        this.challengeNames = challengeNames
        challenges = arrayOfNulls(challengeNames.size)

        transaction {
            var i = 0
            for (cName in challengeNames) {
                if (cName == "X") {  // null placeholder
                    continue;
                }
                val cIterator = CHALLENGE.find { CHALLENGES.name eq cName }.iterator()
                val c = cIterator.next().deEntify()
                challenges[i] = c
                i++
            }
        }
    }

    constructor()

    // Log the result for the specified Challenge then:
    // + log a win on all lower level ones
    // + log a loss on all higher level ones
    fun massLog(targetChallenge: String, result: Double) {
        var seenIndex = -1  // index the target was seen at
        var i = 0

        for (challenge in challenges) {
            if (challenge == null) continue
            println()
            var which = 0.0
            if (seenIndex == -1) {
                which = result
                if (challenge.name == targetChallenge) {
                    seenIndex = i
                }
            } else if (i > seenIndex) {
                which = 0.0
            }

            Logger.cManual(challenge.name as String, which)
            i++
        }
    }

    override fun print() {
        TODO()
    }

    fun printSeq() {  // print the sequence of challenges for the current progression
        for (challenge in challenges) {
            if (challenge == null) continue
            print(format(challenge.name, Styler.colorByElo(challenge.challengeElo), 20))
        }
    }

    override fun printL() {
        print(icon_name_skill())
        print("  ")
        printSeq()
        println()
    }
}