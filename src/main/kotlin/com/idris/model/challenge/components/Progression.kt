package com.idris.model.challenge.components
import com.idris.Database
import com.idris.constants.Styles
import com.idris.constants.Styles.RED
import com.idris.constants.Styles.YELLOW
import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.database.helpers.LogHelper
import com.idris.model.objective.Challenge
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// A Progression is an ordered set of Challenges of progressive difficulty

// EX. V7in7 -> V7in5 -> V7in3 -> V7in1


class Progression(val name: String, val challengeNames: List<String>, database: Database) {
    var challenges: Array<Challenge?> = arrayOfNulls<Challenge>(challengeNames.size)

    init {  // load named Challenges from the database
        transaction {
            var i = 0
            for (cName in challengeNames) {
                println("$cName")
                if (cName == "X") {  // null placeholder
                    continue;
                }
                val cIterator = ChallengeE.find { ChallengesT.name eq cName }.iterator()
                // System.out.println("Found $cName");
                val c = cIterator.next().deEntify()
                c.printShort(0)
                challenges[i] = c
                i++
            }
        }
    }

    fun print() {
        print("\n${Styles.BOLD}$name${Styles.RESET} (")
        var c = 0;
        for (challenge in challenges) {
            if (challenge?.equals("X") as Boolean) {  // null placeholder
                continue;
            }
            if (c != 0) print(" -> ")
            var style = ""
            challenge?.challengeElo?.let {
                if (it >= 1600 && it < 1700) {
                    style = Styles.GREEN
                } else if (it >= 1700 && it < 1800) {
                    style = Styles.BLUE
                } else if (it >= 1800 && it < 1900) {
                    style = Styles.YELLOW
                } else if (it >= 1900) {
                    style = Styles.RED;
                }
            }
            print("\n  ${style}${challenge?.name}${Styles.RESET}")
            c++;
        }
        print(")\n")
    }


    // Log the result for the specified Challenge then:
    // + log a win on all lower level ones
    // + log a loss on all higher level ones
    fun massLog(targetChallenge: String, result: Double) {
        var seenIndex = -1  // index the target was seen at
        var i = 0

        for (challenge in challenges) {
            println()
            var which = 0.0
            if (seenIndex == -1) {
                which = result
                if (challenge?.name == targetChallenge) {
                    seenIndex = i
                }
            } else if (i > seenIndex) {
                which = 0.0
            }

            LogHelper.cManual(challenge?.name as String, which)
            i++
        }
    }
}

fun main() {
    // The V7inN progression tracks increasingly difficult challenges (completing a V7 in decreasingly fewer moves)
    val path = "sdata/realData.db"
    val data = Database(path)

    val dreamQualityNames = listOf(
        "dream-quality-average",
        "dream-quality-competent",
        "dream-quality-skilled")

    val dreamQuantityNames = listOf(
        "dream-quantity-average",
        "dream-quantity-competent",
        "dream-quantity-skilled")

    val gbcTimeNames = listOf(
        "gbc-sub14",
        "gbc-sub12",
        "gbc-sub10",
        "gbc-sub8",
        "gbc-sub6")

    val dreamQuality = Progression("dream-quality-N", dreamQualityNames, data)
    val dreamQuantity = Progression("dream-quantity-N", dreamQuantityNames, data)
    val gbcTime = Progression("gbc-subN", gbcTimeNames, data)
    // dreamQuality.massLog("dream-quality-competent", 0.0)
    // dreamQuantity.massLog("dream-quantity-skilled", 1.0)
    gbcTime.massLog("gbc-sub12", 1.0)

    dreamQuality.print()
    dreamQuantity.print()
}