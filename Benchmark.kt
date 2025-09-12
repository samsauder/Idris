// Benchmark

import StyleConstants.GREEN
import StyleConstants.YELLOW
import StyleConstants.RED
import StyleConstants.BLUE
import StyleConstants.BOLD
import StyleConstants.RESET

import elo.EloTool
import java.time.LocalDate


class Benchmark(id: String, skill: Skill, val description: String, minutes: Int, var odds: Double) : Objective(id, skill, minutes) {
    override val objectiveType = ObjectiveType.BENCHMARK

    val et = EloTool()
    var userElo: Double = 1500.0
    var benchmarkElo: Double = 0.0

    init {
        benchmarkElo = et.opponentRating(userElo, odds)
    }

    // Fast rule of thumb
    fun computeBenchmarkEloTemp(likelihood: Double): Double {
        when (likelihood) {
            0.10 -> {return userElo + 400}
            0.25 -> {return userElo + 200}
            0.40 -> {return userElo + 100}
            0.50 -> {return userElo}
            0.60 -> {return userElo - 100}
            0.75 -> {return userElo - 200}
            0.90 -> {return userElo - 400}
            1.0 -> {return 0.0}
        }
        return -1.0
    }

    // value is the result of the attempt for the user (1.0 or 0.5 or 0.0)
    override fun log(value: Double) {
        // println("(log)")
        val eU = et.expectedOutcome(userElo, benchmarkElo)  // expected user outcome
        val eB = et.expectedOutcome(benchmarkElo, userElo)  // expected benchmark outcome
        var sU: Double = -1.0  // actual user outcome
        var sB: Double = -1.0  // actual benchmark outcome

        when (value) {
            1.0 -> {
                // print("$GREEN[+]$RESET")
                sU = 1.0
                sB = 0.0
            }  // user won
            0.0 -> {
                // print("${RED}[-]$RESET")
                sU = 0.0
                sB = 1.0
            }  // benchmark won
            0.5 -> {
                sU = 0.5
                sB = 0.5
                // print("[=]")
            } // no winner
            else -> {}
        }

        // val userEloOld = getUserEloString()
        // val benchmarkEloOld = getBenchmarkEloString()
        // val oddsOld = odds

        userElo = et.newRating(userElo, 40, sU, eU)
        benchmarkElo = et.newRating(benchmarkElo, 20, sB, eB)
        odds = et.expectedOutcome(userElo, benchmarkElo)

        // println(" $BOLD${this.id}$RESET on ${LocalDate.now()}")
        // println("bELO: $RED$benchmarkEloOld$RESET -> $RED${getBenchmarkEloString()}$RESET")
        // println("uELO: $BLUE$userEloOld$RESET -> $BLUE${getUserEloString()}$RESET")
        // println("ODDS:  ${(oddsOld * 100).toInt()}% -> ${(odds * 100).toInt()}%")
    }

    var color = YELLOW  // desired benchmark color
    val coloredBenchmark = "$color[BENCHMARK ●]$RESET"

    fun print(startLevel: Int) {
        val lvl0: String = " ".repeat(startLevel * 4)
        //val lvl1: String = " ".repeat((startLevel + 1) * 4)
        // val benchmarkEloString = getBenchmarkEloString(benchmarkElo)

        val color: String = YELLOW

        println("$lvl0$coloredBenchmark $RED${getBenchmarkEloString()}$RESET $BLUE${getUserEloString()}$RESET $GREEN${minutes}m$RESET $id")

        val coloredBenchmarkIndented: String = "$lvl0$coloredBenchmark"

        /* Detailed benchmark information
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
        val benchEloStr = getBenchmarkEloString() // benchmark elo
        val userEloStr = getUserEloString()

        println("$lvl$coloredBenchmark $BOLD${skill.id}$RESET $RED$benchEloStr $BLUE$userEloStr$RESET $GREEN${minutes}m$RESET $id")
    }

    // Returns a string of the elo (turns -Infinity to 0000.0)
    fun getBenchmarkEloString() : String {
        var benchElo: String = kotlin.math.round(benchmarkElo).toInt().toString()
        if (benchmarkElo == Double.NEGATIVE_INFINITY) {
            benchElo = "0000"
        }
        return benchElo
    }

    fun getUserEloString() : String {
        return kotlin.math.round(userElo).toInt().toString()
    }
}