package com.idris.model.objective

import com.idris.constants.Styles
import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.elo.EloTool
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType
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

    // for keeping track of provisional challenge elo (no updating user elo)
    var attempts: Int = 0  // how many times has the user attempted
    var wins: Int = 0  // how many times has the user won


    // General Constructor (just makes the core Objective attributes)
    constructor(
        name: String,
        skill: Skill?,
        description: String,
        minutes: Double
    ) : super(name, skill, description, minutes) {
        this.symbol = "●"
        this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
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

    // Calculate the challenge elo from the record so far (for provisional challenge elo)
    fun regenerateChallengeElo() {
        challengeElo = if (wins == attempts) {
            0000.0
        } else if (wins == 0 && attempts > 0) {
            3000.0
        } else {
            et.opponentRating(1500.0, wins/(attempts*1.0))
        }
    }


    override fun printShort(startLevel: Int) {
        val provSym = if (attempts < 20) "?" else ""  // provisional symbol (?)

        val lvl = " ".repeat(startLevel * 4)                                          // indent level
        val chEloStr = styleAndPad("${getChallengeEloString()}$provSym", Styles.RED, 5)  // challenge elo + style + padding
        val usEloStr = styleAndPad(getUserEloString(), Styles.BLUE, 5)      // user elo + style + padding
        val usOddsStr = styleAndPad("${(100*userOdds).roundToInt()}%", Styles.BOLD, 5)        // user odds + style

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
                print("${Styles.GREEN}[+]${Styles.RESET}")
                sU = 1.0
                sB = 0.0
                wins++
            }  // user won
            0.0 -> {
                print("${Styles.RED}[-]${Styles.RESET}")
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
        attempts++

        // println("wins/attempts now: $wins/$attempts")

        val userEloOld = getUserEloString()
        val challengeEloOld = getChallengeEloString()
        val oddsOld = userOdds

        println(" ${Styles.BOLD}${name}${Styles.RESET} on ${LocalDate.now()}")

        if (attempts < 20) {  // provisional (update just the challenge elo)
            userOdds = wins / (attempts * 1.0)
            regenerateChallengeElo()
            println("    ${Styles.ITALIC}cELO${Styles.RESET} | ${challengeEloOld}? -> ${Styles.RED}${getChallengeEloString()}?${Styles.RESET}")
        } else {  // update both ratings
            userElo = et.newRating(userElo, 40, sU, eU)
            challengeElo = et.newRating(challengeElo, 40, sB, eB)
            userOdds = et.expectedOutcome(userElo, challengeElo)

            println("    ${Styles.ITALIC}uELO${Styles.RESET} | $userEloOld -> ${Styles.BLUE}${getUserEloString()}${Styles.RESET}")
            println("    ${Styles.ITALIC}cELO${Styles.RESET} | $challengeEloOld -> ${Styles.RED}${getChallengeEloString()}${Styles.RESET}")
        }

        println("    ${Styles.ITALIC}ODDS${Styles.RESET} |  ${(oddsOld * 100).toInt()}% -> ${Styles.GREEN}${(userOdds * 100).toInt()}%${Styles.RESET}")
    }


    // writes cElo, uElo and uOdds to the database at <dbFilePath>.db
    // the dbFilePath is of the form /directory/filename.db
    fun writeToDB(dbFilePath: String) {
        Database.Companion.connect("jdbc:sqlite:${dbFilePath}", "org.sqlite.JDBC")
        TransactionManager.Companion.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

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