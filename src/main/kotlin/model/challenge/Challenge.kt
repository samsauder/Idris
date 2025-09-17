// Challenge
// TODO: structure a Challenge as <core><constraintList>

package model.challenge

import constants.Styles
import elo.EloTool
import model.Objective
import model.Skill
import model.auxiliary.ObjectiveType
import java.time.LocalDate
import kotlin.math.round

class Challenge(id: String, skill: Skill, val description: String, minutes: Double, var odds: Double) : Objective(id, skill, minutes) {
    override val objectiveType = ObjectiveType.CHALLENGE

    val et = EloTool()
    var userElo: Double = 1500.0
    var challengeElo: Double = 0.0

    init {
        challengeElo = et.opponentRating(userElo, odds)
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
        val oddsOld = odds

        userElo = et.newRating(userElo, 40, sU, eU)
        challengeElo = et.newRating(challengeElo, 20, sB, eB)
        odds = et.expectedOutcome(userElo, challengeElo)

        println(" ${Styles.BOLD}${this.id}${Styles.RESET} on ${LocalDate.now()}")
        println("    ${Styles.ITALIC}bELO${Styles.RESET} | $benchmarkEloOld -> ${Styles.RED}${getChallengeEloString()}${Styles.RESET}")
        println("    ${Styles.ITALIC}uELO${Styles.RESET} | $userEloOld -> ${Styles.BLUE}${getUserEloString()}${Styles.RESET}")
        println("    ${Styles.ITALIC}ODDS${Styles.RESET} |  ${(oddsOld * 100).toInt()}% -> ${Styles.YELLOW}${(odds * 100).toInt()}%${Styles.RESET}")
    }

    var color = Styles.YELLOW  // desired benchmark color
    val coloredBenchmark = "$color[CHALLENGE ●]${Styles.RESET}"


    fun print(startLevel: Int) {
        val lvl0: String = " ".repeat(startLevel * 4)

        println("$lvl0$coloredBenchmark ${Styles.RED}${getChallengeEloString()}${Styles.RESET} ${Styles.BLUE}${getUserEloString()}${Styles.RESET} ${Styles.GREEN}${minutes}m${Styles.RESET} $id")

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


    override fun printShort(startLevel: Int) {
        val lvl = " ".repeat(startLevel * 4)
        val benchEloStr = getChallengeEloString() // benchmark elo
        val userEloStr = getUserEloString()

        println("$lvl$coloredBenchmark ${Styles.BOLD}${skill.id}${Styles.RESET} ${Styles.RED}$benchEloStr ${Styles.BLUE}$userEloStr${Styles.RESET} ${Styles.GREEN}${minutes}m${Styles.RESET} $id")
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