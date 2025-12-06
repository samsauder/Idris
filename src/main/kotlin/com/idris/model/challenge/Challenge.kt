// Challenge
// TODO: structure a Challenge as <core><constraintList>

package com.idris.model.challenge

import com.idris.constants.Styles
import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.elo.EloTool
import com.idris.model.Objective
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType
import com.idris.constants.Styles.BLUE
import com.idris.constants.Styles.BOLD
import com.idris.constants.Styles.GREEN
import com.idris.constants.Styles.ITALIC
import com.idris.constants.Styles.RED
import com.idris.constants.Styles.RESET
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.sql.Connection
import java.time.LocalDate
import kotlin.math.round
import kotlin.math.roundToInt

class Challenge : Objective {
    val et = EloTool()
    var userElo: Double = 1500.0
    var challengeElo: Double = 0.0
    var userOdds: Double = 0.0


    // General Constructor (just makes the core Objective attributes)
    constructor(
        name: String,
        skill: Skill?,
        description: String,
        minutes: Double
    ) : super(name, skill, description, minutes) {
        this.symbol = "●"
        this.symbolHolder = "$symbolColor[$symbol]$RESET"
        this.objectiveType = ObjectiveType.CHALLENGE
    }


    // Elo Determining Constructor (given user odds)
    constructor(
        name: String,
        skill: Skill?,
        description: String,
        minutes: Double,
        userOdds: Double
    ) : this(name, skill, description, minutes) {  // userElo and challengeElo are determined
        this.userOdds = userOdds
        this.userElo = 1500.00
        this.challengeElo = et.opponentRating(userElo, userOdds)
    }


    // Elo Supplying Constructor
    constructor(name: String,
                skill: Skill,
                description: String,
                minutes: Double,
                challengeElo: Double,
                userElo: Double,
                userOdds: Double) : this(name, skill, description, minutes) {  // userElo and challengeElo are given
        this.challengeElo = challengeElo
        this.userElo = userElo
        this.userOdds = userOdds
    }


    init {
        // challengeElo = et.opponentRating(userElo, userOdds)
        generateChallengeElo()
    }


    fun generateChallengeElo() {
        challengeElo = et.opponentRating(userElo, userOdds)
    }


    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)                                          // indent level
        val chEloStr = styleAndPad(getChallengeEloString(), Styles.RED, 5)  // challenge elo + style + padding
        val usEloStr = styleAndPad(getUserEloString(), Styles.BLUE, 5)      // user elo + style + padding
        val usOddsStr = styleAndPad("${(100*userOdds).roundToInt()}%", Styles.GREEN, 5)        // user odds + style

        println("$lvl$symbolHolder $nameStr $skillStr $minsStr $chEloStr $usEloStr $usOddsStr  $descriptionStr")
    }


    // value is the result of the attempt for the user (1.0 or 0.5 or 0.0)
    override fun log(value: Double) {
        // println("(log)")
        val eU = et.expectedOutcome(userElo, challengeElo)  // expected user outcome
        val eB = et.expectedOutcome(challengeElo, userElo)  // expected benchmark outcome
        var sU: Double = -1.0  // actual user outcome
        var sB: Double = -1.0  // actual benchmark outcome

        when (value) {
            1.0 -> {
                print("${GREEN}[+]${RESET}")
                sU = 1.0
                sB = 0.0
            }  // user won
            0.0 -> {
                print("${RED}[-]${RESET}")
                sU = 0.0
                sB = 1.0
            }  // benchmark won
            0.5 -> {
                // print("[=]")
                sU = 0.5
                sB = 0.5
            } // no winner
            else -> {}
        }

        val userEloOld = getUserEloString()
        val benchmarkEloOld = getChallengeEloString()
        val oddsOld = userOdds

        userElo = et.newRating(userElo, 40, sU, eU)
        challengeElo = et.newRating(challengeElo, 20, sB, eB)
        userOdds = et.expectedOutcome(userElo, challengeElo)

        println(" ${BOLD}${name}${RESET} on ${LocalDate.now()}")
        println("    ${ITALIC}bELO${RESET} | $benchmarkEloOld -> ${RED}${getChallengeEloString()}${RESET}")
        println("    ${ITALIC}uELO${RESET} | $userEloOld -> ${BLUE}${getUserEloString()}${RESET}")
        println("    ${ITALIC}ODDS${RESET} |  ${(oddsOld * 100).toInt()}% -> ${GREEN}${(userOdds * 100).toInt()}%${RESET}")
    }


    // writes cElo, uElo and uOdds to the database at <dbFilePath>.db
    // the dbFilePath is of the form /directory/filename.db
    fun writeToDB(dbFilePath: String) {
        Database.connect("jdbc:sqlite:${dbFilePath}", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            val challengeE = ChallengeE.Companion.findSingleByAndUpdate(ChallengesT.name eq name) {
                it.cElo = BigDecimal.valueOf(challengeElo)
                it.uElo = BigDecimal.valueOf(userElo)
                it.uOdds = BigDecimal.valueOf(userOdds)
            }
        }
    }


    // Returns a string of the elo (turns -Infinity to 0000.0)
    private fun getChallengeEloString() : String {
        var benchElo: String = round(challengeElo).toInt().toString()
        if (challengeElo == Double.NEGATIVE_INFINITY) {
            benchElo = "0000"
        }
        return benchElo
    }


    private fun getUserEloString() : String {
        return round(userElo).toInt().toString()
    }

}