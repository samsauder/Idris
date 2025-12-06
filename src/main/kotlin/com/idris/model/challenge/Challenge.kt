// Challenge
// TODO: structure a Challenge as <core><constraintList>

package model.challenge

import constants.Styles
import constants.Styles.BLUE
import constants.Styles.BOLD
import constants.Styles.GREEN
import constants.Styles.ITALIC
import constants.Styles.RED
import constants.Styles.RESET
import database.ChallengeE
import database.ChallengesT
import elo.EloTool
import model.Objective
import model.Skill
import model.auxiliary.ObjectiveType
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
    // Elo Determining Constructor
    constructor(name: String,
                skill: Skill,
                description: String,
                minutes: Double,
                userOdds: Double) : super(name, skill, description, minutes) {  // userElo and challengeElo are determined
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
                userOdds: Double) : super(name, skill, description, minutes) {  // userElo and challengeElo are given
        this.userOdds = userOdds
        this.userElo = userElo
        this.challengeElo = challengeElo
    }

    override val objectiveType = ObjectiveType.CHALLENGE

    val et = EloTool()
    var userElo: Double = 1500.0
    var challengeElo: Double = 0.0
    var userOdds: Double = 0.0

    init {
        challengeElo = et.opponentRating(userElo, userOdds)
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

        val userEloOld = getUserEloString()
        val benchmarkEloOld = getChallengeEloString()
        val oddsOld = userOdds

        userElo = et.newRating(userElo, 40, sU, eU)
        challengeElo = et.newRating(challengeElo, 20, sB, eB)
        userOdds = et.expectedOutcome(userElo, challengeElo)

        println(" ${Styles.BOLD}${name}${Styles.RESET} on ${LocalDate.now()}")
        println("    ${Styles.ITALIC}bELO${Styles.RESET} | $benchmarkEloOld -> ${Styles.RED}${getChallengeEloString()}${Styles.RESET}")
        println("    ${Styles.ITALIC}uELO${Styles.RESET} | $userEloOld -> ${Styles.BLUE}${getUserEloString()}${Styles.RESET}")
        println("    ${Styles.ITALIC}ODDS${Styles.RESET} |  ${(oddsOld * 100).toInt()}% -> ${Styles.GREEN}${(userOdds * 100).toInt()}%${Styles.RESET}")

        /*
        // Connect to database and write changes
        Database.connect("jdbc:sqlite:data/realData.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE


        transaction {
            val challengeE = ChallengeE.findSingleByAndUpdate(ChallengesT.name eq name) {
                it.cElo = BigDecimal.valueOf(challengeElo)
                it.uElo = BigDecimal.valueOf(userElo)
                it.uOdds = BigDecimal.valueOf(userOdds)
            }
        }
        */
    }

    // writes cElo, uElo and uOdds to the database at <dbFilePath>.db
    // the dbFilePath is of the form /directory/filename.db
    fun writeToDB(dbFilePath: String) {
        Database.connect("jdbc:sqlite:${dbFilePath}", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            val challengeE = ChallengeE.findSingleByAndUpdate(ChallengesT.name eq name) {
                it.cElo = BigDecimal.valueOf(challengeElo)
                it.uElo = BigDecimal.valueOf(userElo)
                it.uOdds = BigDecimal.valueOf(userOdds)
            }
        }
    }

    var color = Styles.YELLOW  // desired benchmark color
    val coloredChallenge = "$color[●]${Styles.RESET}"


    fun print(startLevel: Int) {
        val lvl0: String = " ".repeat(startLevel * 4)

        println("$lvl0$coloredChallenge ${Styles.RED}${getChallengeEloString()}${Styles.RESET} ${Styles.BLUE}${getUserEloString()}${Styles.RESET} ${Styles.GREEN}${minutes}m${Styles.RESET} $name $ITALIC${userOdds}$RESET")

        /* Detailed benchmark information
        val coloredBenchmarkIndented: String = "$lvl0$coloredBenchmark"

        println(coloredBenchmarkIndented)
        println("${lvl1}ID             ${ITALIC}$id${RESET}")
        println("${lvl1}DESCRIPTION    $description")
        println("${lvl1}SKILL          ${skill.id}")
        println("${lvl1}MINUTES        $GREEN$minutes$RESET")
        println("${lvl1}BENCHMARK ELO  ${RED}${getBenchmarkEloString()}${RESET}")
        println("${lvl1}USER ELO       ${BLUE}${getUserEloString()}${RESET}")
        println("${lvl1}ODDS           ${(odds*100).toInt()}%")
        */
    }

    fun style(s: String, style: String) : String{
        return "$style$s$RESET"
    }

    // Returns a whitespace-padded version of the given string
    fun pad(s: String, padLen: Int) : String {
        return s.padEnd(padLen, ' ')
    }

    // Returns an ansi-styled and whitespace-padded version of the given string
    fun styleAndPad(s: String, style: String?, padLen: Int) : String {
        return "$style${s.padEnd(padLen, ' ')}$RESET"
    }

    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)                                          // indent level
        val nameStr = pad(name,  18)                                          // name + style + padding
        val skillStr = styleAndPad(skill.id, BOLD, 10)               // skill + style + padding
        val minsStr = styleAndPad("${minutes.toInt()}m", ITALIC, 5)             // mins + style + padding
        val chEloStr = styleAndPad(getChallengeEloString(), RED, 5)  // challenge elo + style + padding
        val usEloStr = styleAndPad(getUserEloString(), BLUE, 5)      // user elo + style + padding
        val usOddsStr = style("${(100*userOdds).roundToInt()}%", GREEN)        // user odds + style

        println("$lvl$coloredChallenge $nameStr $skillStr $minsStr $chEloStr $usEloStr $usOddsStr")
    }


    // Returns a string of the elo (turns -Infinity to 0000.0)
    fun getChallengeEloString() : String {
        var benchElo: String = round(challengeElo).toInt().toString()
        if (challengeElo == Double.NEGATIVE_INFINITY) {
            benchElo = "0000"
        }
        return benchElo
    }


    fun getUserEloString() : String {
        return round(userElo).toInt().toString()
    }
}