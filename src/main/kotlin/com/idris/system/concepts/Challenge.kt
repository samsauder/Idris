package com.idris.system.concepts

import com.idris.system.extra.Styles
import com.idris.system.extra.EloTool
import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Styler
import com.idris.system.extra.Styler.format
import java.time.LocalDate
import kotlin.math.round
import kotlin.math.roundToInt


class Challenge : Objective {
    override val icon = "●"
    var userElo: Double = 1500.0
    var challengeElo: Double = 0.0
    var userOdds: Double = 0.0
    var progressionName: String = ""
    var attempts: Int = 0  // how many times has the user attempted
    var wins: Int = 0  // how many times has the user won
    val et = EloTool()

    // General Constructor (just makes the core Objective attributes)
    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double) : super(name, skillName, description, minutes) {
        this.symbol = "●"
        this.symbolHolder = "$symbolColor[$symbol]${Styles.RESET}"
        this.objectiveType = ObjectiveType.CHALLENGE
    }

    // Elo Determining Constructor (given user odds)
    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double,
                userOdds: Double) : this(name, skillName, description, minutes) {  // userElo and challengeElo are determined
        this.userOdds = userOdds
        this.userElo = 1500.00
        this.challengeElo = et.opponentRating(userElo, userOdds)
    }

    // Elo Supplying Constructor
    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double,
                challengeElo: Double,
                userElo: Double,
                userOdds: Double) : this(name, skillName, description, minutes) {  // userElo and challengeElo are given
        this.challengeElo = challengeElo
        this.userElo = userElo
        this.userOdds = userOdds
    }

    init {
        generateChallengeElo()
    }

    // value is the result of the attempt for the user (1.0 or 0.5 or 0.0)
    override fun log(won: Boolean) {
        val eUo = et.expectedOutcome(userElo, challengeElo)  // expected user outcome
        val eCo = et.expectedOutcome(challengeElo, userElo)  // expected challenge outcome
        var aUo: Double   // actual user outcome
        var aCo: Double   // actual challenge outcome

        if (won) {
            print("${Styles.GREEN}[+]${Styles.RESET}")
            aUo = 1.0
            aCo = 0.0
            wins++
        } else {
            print("${Styles.RED}[-]${Styles.RESET}")
            aUo = 0.0
            aCo = 1.0
        }
        
        attempts++

        originalLogSequence(eUo, aUo, eCo, aCo)
    }

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        println(icon_name_skill_elo_odds())
    }

    // Return a string composed of the Challenge icon, name, skill, elo, and odds
    private fun icon_name_skill_elo_odds(): String {
        val qm = if (attempts < 20) "?" else " "  // a question mark indicates provisional
        val elof = format("${celoS()}$qm", Styler.colorByElo(challengeElo), 10)  // formatted elo
        val oddsf = format("${(100*userOdds).roundToInt()}%", Styles.ITALIC, 4)    // formatted odds
        return "${icon_name_skill()}$elof$oddsf"
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

    // In which challenge elo and user elo are both updated after each log (or just challenge elo if it is provisional)
    // ARGS:
    // * euo (expected user outcome)
    // * auo (actual user outcome)
    // * eco (expected challenge outcome)
    // * aco (actual challenge outcome)
    fun originalLogSequence(eUo: Double, aUo: Double, eCo: Double, aCo: Double) {
        val provSymOld = if (attempts < 20) "?" else ""  // provisional symbol (?)
        val challengeEloOld = format("${celoS()}$provSymOld", Styler.colorByElo(challengeElo), null)
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
        val challengeEloNew = format("${celoS()}$provSymNew", Styler.colorByElo(challengeElo), null)

        val userOddsAsPercentageOld = (oddsOld * 100).toInt()
        val userOddsAsPercentageNew = (userOdds * 100).toInt()

        println("    CHALLENGE ELO      |  $challengeEloOld -> $challengeEloNew")
        println("    ODDS               |  $userOddsAsPercentageOld% -> $userOddsAsPercentageNew%")
    }


    // Returns a string of the challenge elo (turns -Infinity to 0000.0)
    private fun celoS() : String {
        var benchElo: String = round(challengeElo).toInt().toString()
        if (challengeElo == Double.NEGATIVE_INFINITY) benchElo = "0000"
        return benchElo
    }




}