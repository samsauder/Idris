package com.idris.system.concepts
import com.idris.system.extra.Styles
import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.operators.Logger
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


class Progression : Concept {
    override var icon = "P"
    var challengeNames: List<String>
    var challenges: Array<Challenge?>

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
        print(format(name, Styles.BOLD, 20))
        print(" (")

        var c = 0;
        for (challenge in challenges) {
            if (challenge == null) continue
            if (c != 0) print(" -> ")
            print(format(challenge.name, challenge.color(), null))
            c++;
        }
        print(")\n")

    }

    override fun printL() {
        println(icon_name_skill())
    }
}