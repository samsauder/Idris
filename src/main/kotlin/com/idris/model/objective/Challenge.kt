package com.idris.model.objective

import com.idris.constants.Styles
import com.idris.elo.EloTool
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType
import java.time.LocalDate
import kotlin.math.round
import kotlin.math.roundToInt


class Challenge : Objective {
    val et = EloTool()
    override val icon = "●"
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
        //val usEloStr = pad(getUserEloString(), 5)      // user elo + style + padding

        // val chEloStrOrig = styleAndPad("${getChallengeEloString()}$provSym", Styles.RED, 5)  // challenge elo + style + padding
        // val usEloStrOrig = styleAndPad(getUserEloString(), Styles.BLUE, 5)      // user elo + style + padding

        val usOddsStr = styleAndPad("${(100*userOdds).roundToInt()}%", Styles.ITALIC, 4)        // user odds + style

        val progressionStr = styleAndPad("$progressionName", Styles.BOLD, 20)
        // println("$lvl$symbolHolder $nameStr $progressionStr $skillStr $minsStr [ $chEloStr $usEloStr]  $usOddsStr  $descriptionStr")
        println("$lvl$symbolHolder $nameStr | $chEloStr $usOddsStr         $progressionStr $skillStr $minsStr $descriptionStr")
    }


    // value is the result of the attempt for the user (1.0 or 0.5 or 0.0)
    override fun log(value: Double) {
        // println("(log)")
        val eUo = et.expectedOutcome(userElo, challengeElo)  // expected user outcome
        val eCo = et.expectedOutcome(challengeElo, userElo)  // expected challenge outcome
        var aUo: Double = -1.0  // actual user outcome
        var aCo: Double = -1.0  // actual challenge outcome

        when (value) {
            1.0 -> {
                print("${Styles.GREEN}[+]${Styles.RESET}")
                aUo = 1.0
                aCo = 0.0
                wins++
            }  // user won
            0.0 -> {
                print("${Styles.RED}[-]${Styles.RESET}")
                aUo = 0.0
                aCo = 1.0
            }  // benchmark won
            0.5 -> {
                // print("[=]")
                aUo = 0.5
                aCo = 0.5
            } // no winner
            else -> {}
        }
        attempts++

        originalLogSequence(eUo, aUo, eCo, aCo)
        // updatedLogSequence()
        /*
        val userEloOld = getUserEloString()
        val provSymOld = if (attempts < 20) "?" else ""  // provisional symbol (?)
        val challengeEloOld = colorByOwnElo("${getChallengeEloString()}$provSymOld")
        val oddsOld = userOdds

        println(" ${Styles.BOLD}${name}${Styles.RESET} on ${LocalDate.now()}")

        if (attempts < 20) {  // provisional (update just the challenge elo)
            userOdds = wins / (attempts * 1.0)
            regenerateChallengeElo()
        } else {  // update both ratings
            userElo = et.newRating(userElo, 40, aUo, eUo)
            challengeElo = et.newRating(challengeElo, 40, aCo, eCo)
            userOdds = et.expectedOutcome(userElo, challengeElo)
            val userEloNew = getUserEloString()

            println("    USER ELO           |  $userEloOld -> $userEloNew")
        }

        val provSymNew: String = if (attempts < 20) "?" else ""  // provisional symbol (?)
        val challengeEloNew = colorByOwnElo("${getChallengeEloString()}$provSymNew")

        val userOddsAsPercentageOld = (oddsOld * 100).toInt()
        val userOddsAsPercentageNew = (userOdds * 100).toInt()

        println("    CHALLENGE ELO      |  $challengeEloOld -> $challengeEloNew")
        println("    ODDS               |  $userOddsAsPercentageOld% -> $userOddsAsPercentageNew%")
        */
    }


    // In which challenge elo and user elo are both updated after each log (or just challenge elo if it is provisional)
    // ARGS:
    // * euo (expected user outcome)
    // * auo (actual user outcome)
    // * eco (expected challenge outcome)
    // * aco (actual challenge outcome)
    fun originalLogSequence(eUo: Double, aUo: Double, eCo: Double, aCo: Double) {
        // val userEloOld = getUserEloString()
        val provSymOld = if (attempts < 20) "?" else ""  // provisional symbol (?)
        val challengeEloOld = colorByOwnElo("${getChallengeEloString()}$provSymOld")
        val oddsOld = userOdds

        println(" ${Styles.BOLD}${name}${Styles.RESET} on ${LocalDate.now()}")

        // previously: if (attempts < 20)
        if (true) {  // provisional (update just the challenge elo)
            userOdds = wins / (attempts * 1.0)
            regenerateChallengeElo()
        } else {  // update both ratings
            /*
            userElo = et.newRating(userElo, 40, aUo, eUo)
            challengeElo = et.newRating(challengeElo, 40, aCo, eCo)
            userOdds = et.expectedOutcome(userElo, challengeElo)
            val userEloNew = getUserEloString()

            println("    USER ELO           |  $userEloOld -> $userEloNew")
           */
        }

        val provSymNew: String = if (attempts < 20) "?" else ""  // provisional symbol (?)
        val challengeEloNew = colorByOwnElo("${getChallengeEloString()}$provSymNew")

        val userOddsAsPercentageOld = (oddsOld * 100).toInt()
        val userOddsAsPercentageNew = (userOdds * 100).toInt()

        println("    CHALLENGE ELO      |  $challengeEloOld -> $challengeEloNew")
        println("    ODDS               |  $userOddsAsPercentageOld% -> $userOddsAsPercentageNew%")
    }


    // In which challenge elo is kept at a constant 1500 and user elo is calculated based on the odds after each log
    fun updatedLogSequence() {

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
        val l0 = Styles.BLUE
        val l1 = Styles.CYAN
        val l2 = Styles.GREEN
        val l3 = Styles.YELLOW
        val l4 = Styles.RED
        val reset = Styles.RESET

        if (challengeElo in 1500.0..<1600.0) {
            style = l0
        } else if (challengeElo in 1600.0..<1700.0) {
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


    // Return a conditionally ANSI colored string depending on the current Challenge's user elo
    fun colorByUserElo(str: String): String {
        var style = ""
        val l0 = Styles.CYAN
        val l1 = Styles.BLUE
        val l2 = Styles.GREEN
        val l3 = Styles.YELLOW
        val l4 = Styles.RED
        val reset = Styles.RESET

        if (userElo in 1500.0..<1600.0) {
            style = l0
        } else if (userElo in 1600.0..<1700.0) {
            style = l1
        } else if (userElo in 1700.0..<1800.0) {
            style = l2
        } else if (userElo in 1800.0..<1900.0) {
            style = l3
        } else if (userElo >= 1900.0) {
            style = l4
        }
        return "$style$str$reset"
    }

}