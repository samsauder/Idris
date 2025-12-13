package com.idris.model.objective

import com.idris.constants.Styles
import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.elo.EloTool
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType
import com.idris.sampleData.colorRecall
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
    var progressionName: String = ""

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
        val provSym = if (attempts < 20) "?" else " "  // provisional symbol (?)

        val lvl = " ".repeat(startLevel * 4)                                          // indent level

        val conditionalCElo = this.colorByOwnElo("${getChallengeEloString()}$provSym")
        val chEloStr = styleAndPad(conditionalCElo, Styles.BOLD, 5)  // challenge elo + style + padding
        val usEloStr = pad(getUserEloString(), 5)      // user elo + style + padding

        // val chEloStrOrig = styleAndPad("${getChallengeEloString()}$provSym", Styles.RED, 5)  // challenge elo + style + padding
        // val usEloStrOrig = styleAndPad(getUserEloString(), Styles.BLUE, 5)      // user elo + style + padding

        val usOddsStr = styleAndPad("${(100*userOdds).roundToInt()}%", Styles.ITALIC, 5)        // user odds + style

        val progressionStr = styleAndPad("$progressionName", Styles.BOLD, 20)
        println("$lvl$symbolHolder $nameStr $progressionStr $skillStr $minsStr [ $chEloStr $usEloStr]  $usOddsStr  $descriptionStr")
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

        val userEloOld = colorByOwnElo(getUserEloString())
        val challengeEloOld = colorByOwnElo(getChallengeEloString())
        val provSymOld = if (attempts < 20) "?" else ""  // provisional symbol (?)

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

            val userEloNew = colorByOwnElo(getUserEloString())
            val challengeEloNew = colorByOwnElo(getChallengeEloString())
            val provSymNew = if (attempts < 20) "?" else ""  // provisional symbol (?)


            println("    ${Styles.ITALIC}User Elo      |  ${Styles.RESET} | $userEloOld -> $userEloNew")
            println("    ${Styles.ITALIC}Challenge Elo |  ${Styles.RESET} | $challengeEloOld$provSymOld -> $challengeEloNew$provSymNew")
        }

        println("    ${Styles.ITALIC}ODDS${Styles.RESET} |  ${(oddsOld * 100).toInt()}% -> ${Styles.GREEN}${(userOdds * 100).toInt()}%${Styles.RESET}")
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


    // Return a conditionally ANSI colored string depending on the current this object's challengeElo
    fun colorByOwnElo(str: String): String {
        var style = ""
        val l1 = Styles.GREEN
        val l2 = Styles.BLUE
        val l3 = Styles.YELLOW
        val l4 = Styles.RED
        val reset = Styles.RESET

        if (challengeElo in 1600.0..<1700.0) {
            style = l1
        } else if (challengeElo in 1700.0..<1800.0) {
            style = l2
        } else if (challengeElo in 1800.0..<1900.0) {
            style = l3
        } else if (challengeElo >= 1900.0) {
            style = l4
        }
        return "$style$str$reset"
    }
}