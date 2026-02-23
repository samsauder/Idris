package com.idris.system.concepts

import com.idris.system.extra.Styles
import com.idris.system.extra.EloTool
import com.idris.system.extra.ObjectiveType
import com.idris.system.extra.Styler
import com.idris.system.extra.Styler.format
import com.idris.system.extra.Styler.pad
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Util.printAttempt
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
        this.objectiveType = ObjectiveType.CHALLENGE
    }

    constructor()

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
    override fun update(won: Boolean) {
        var aUo = 0.0   // actual user outcome

        if (won) {
            aUo = 1.0
            wins++
        }
        
        attempts++

        // ===================================================================================================
        val provSymBefore = provSym()
        val eloBefore = format("${eloAsString()}$provSymBefore", Styler.colorByChallenge(this), null)
        val oddsOld = userOdds

        printAttempt(name, aUo == 1.0)
        userOdds = wins / (attempts * 1.0)
        regenerateChallengeElo()

        val provSymAfter = provSym()
        val eloAfter = format("${eloAsString()}$provSymAfter", Styler.colorByChallenge(this), null)

        val likelihoodBefore = (oddsOld * 100).toInt()
        val likelihoodAfter = (userOdds * 100).toInt()

        println("    CHALLENGE ELO      |  $eloBefore -> $eloAfter")
        println("    ODDS               |  $likelihoodBefore% -> $likelihoodAfter%\n\n")
    }


    private fun provSym(): String{
        return if (attempts < 20) "?" else ""
    }


    override fun print() {
        conceptCore(12)
        val qm = if (attempts < 20) "?" else " "  // a question mark indicates provisional
        val eloStyle = Styler.colorByChallenge(this)
        val elo = if (challengeElo < 0) "0000$qm" else "${challengeElo.toInt().toString()}$qm"

        print(pad(" ELO", 12))
        println(style("  $elo", eloStyle))

        print(pad(" ODDS", 12))
        println("  ${(userOdds * 100).toInt()}%")

        print(pad(" ATTEMPTS", 12))
        println(style("  $attempts", Styles.ITALIC))

    }

    override fun printL() {
        println(icon_name_skill_elo_odds())  // progression name added temporarily for debugging
    }

    // Return a string composed of the Challenge icon, name, skill, elo, and odds
    private fun icon_name_skill_elo_odds(): String {
        val qm = if (attempts < 20) "?" else " "  // a question mark indicates provisional
        val elof = format("${eloAsString()}$qm", Styler.colorByChallenge(this), 10)  // formatted elo
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

    // Returns a string of the challenge elo (turns -Infinity to 0000.0)
    private fun eloAsString() : String {
        var benchElo: String = round(challengeElo).toInt().toString()
        if (challengeElo == Double.NEGATIVE_INFINITY) benchElo = "0000"
        return benchElo
    }
}