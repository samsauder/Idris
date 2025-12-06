// Flow
// Description: for loading data and controlling the text interface

import sampleData.*

import model.Plan
import model.Day
import model.NonDay
import model.lab.Experiment;

import model.Objective
import model.auxiliary.ObjectiveType
import model.auxiliary.Type

import constants.Styles.GREEN
import constants.Styles.RESET

import constants.MenuComponents.BAR
import constants.MenuComponents.BOTTOM
import constants.MenuComponents.EDITOR
import constants.MenuComponents.EXIT
import constants.MenuComponents.OBJECTIVES
import constants.MenuComponents.CHALLENGES_TOP
import constants.MenuComponents.PLANS
import constants.MenuComponents.PROMPT_SYM
import constants.MenuComponents.STATS
import constants.MenuComponents.TITLE_TOP
import database.ChallengeE
import database.ChallengesT
import model.challenge.Challenge
import model.lab.EDisplayer
import model.lab.TextEDisplayer
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.sql.Connection
import kotlin.system.exitProcess
import constants.Descriptions
import model.Skill

class Flow() {
    var dbFile = ""
    var planList: MutableList<Plan> = mutableListOf()
    var objectiveList: MutableList<Objective> = mutableListOf()

    fun loadSamChess() {
        ///chessStudyDay.objectives.addAll(chessViChallenges)  // add Visualization challenges
        chessStudyDay.objectives.addAll(chessTaChallenges)  // add Tactics challenges
        // chessStudyDay.objectives.addAll(chessOpChallenges)  // add Opening challenges
        // chessStudyDay.objectives.addAll(chessStChallenges)  // add Strategy challenges

        ///chessRapidDay.objectives.addAll(chessViChallenges)  // add Visualization challenges
        chessRapidDay.objectives.addAll(chessTaChallenges)  // add Tactics challenges
        ///chessRapidDay.objectives.addAll(chessRaChallenges)  // add Rapid challenges
        // chessRapidDay.objectives.addAll(chessAnChallenges)  // add Analysis challenges

        ///chessGuessDay.objectives.addAll(chessViChallenges)  // add Visualization challenges
        chessGuessDay.objectives.addAll(chessTaChallenges)  // add Tactics challenges
        ///chessGuessDay.objectives.addAll(chessGuChallenges)  // add Guess challenges
        // chessGuessDay.objectives.addAll(chessAnChallenges)  // add Analysis challenges

        chess7D.subplans = mutableListOf(chessStudyDay,
                                         chessRapidDay,
                                         chessRapidDay,
                                         chessStudyDay,
                                         chessRapidDay,
                                         chessRapidDay,
                                         chessGuessDay)

        ///objectiveList.addAll(chessViChallenges)
        objectiveList.addAll(chessTaChallenges)
        ///objectiveList.addAll(chessRaChallenges)
        ///objectiveList.addAll(chessGuChallenges)

        planList.addAll(mutableListOf(chessStudyDay, chessRapidDay, chessGuessDay, chess7D))  // load plans
    }

    // Load data into planList and objectiveList
    fun loadSamBouldering() {
        // Bouldering Week (Version 1a)

        // D1
        boulderingD1Limit.objectives.add(hang20mm90max)  // todo
        boulderingD1Limit.objectives.add(projectBlack)   // challenge
        boulderingD1Limit.objectives.add(newPurple)      // challenge
        boulderingD1Limit.objectives.add(pullup85max)    // todo

        // D2
        boulderingD2Volume.objectives.add(new7Reds120)      // challenge
        boulderingD2Volume.objectives.add(new3x3Reds)    // challenge
        boulderingD2Volume.objectives.add(new4x3Reds)    // challenge
        boulderingD2Volume.objectives.add(new5x3Reds)    // challenge

        // D4
        boulderingD4Limit.objectives.add(hang20mm90max)  // todo
        boulderingD4Limit.objectives.add(projectBlack)   // challenge
        boulderingD4Limit.objectives.add(newPurple)      // challenge
        boulderingD4Limit.objectives.add(pullup85max)    // todo

        // D5
        boulderingD5Volume.objectives.add(new7Reds120)   // challenge
        boulderingD5Volume.objectives.add(new3x3Reds)    // challenge
        boulderingD5Volume.objectives.add(new4x3Reds)    // challenge
        boulderingD5Volume.objectives.add(new5x3Reds)    // challenge


        boulderingWeek1a.subplans = mutableListOf(boulderingD1Limit, boulderingD2Volume, boulderingD4Limit, boulderingD5Volume)

        // load into objectiveList
        objectiveList.addAll(boulderingLiTodos)
        objectiveList.addAll(boulderingLiChallenges)
        objectiveList.addAll(boulderingVoChallenges)

        // load into planList
        planList.addAll(mutableListOf(boulderingD1Limit, boulderingD2Volume, boulderingD4Limit, boulderingD5Volume, boulderingWeek1a))
    }


    fun loadWill() {
        // load into objectiveList
        objectiveList.addAll(karateChallenges)
        objectiveList.addAll(karateTests)
    }

    // Connects to the SQLite database specified by dbFile
    fun connectToSQLiteDB() {
        Database.connect("jdbc:sqlite:${dbFile}", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }

    // Loads all challenges from the real database into the objectiveList
    fun loadChallengesFromRealDatabase() {
        dbFile = "sdata/realData.db"
        connectToSQLiteDB()

        transaction {
            for (challengeE in ChallengeE.all()) {
                val challenge = challengeE.toChallenge()
                objectiveList.add(challenge)  // add the Challenge to the objectiveList

                // if cElo is -0000.00, calculate it from the uElo and uOdds and write it to the database
                if (challengeE.cElo == BigDecimal("-0000.00")) {
                    updateCElo(challengeE.name, challenge.challengeElo)
                }
            }
        }
    }


    // Loads all challenges from the test database into the objectiveList
    fun loadChallengesFromTestDatabase() {
        dbFile = "testdata/testData.db"
        connectToSQLiteDB()

        transaction {
            for (challengeE in ChallengeE.all()) {
                val challenge = challengeE.toChallenge()
                objectiveList.add(challenge)  // add the Challenge to the objectiveList

                // if cElo is -0000.00, calculate it from the uElo and uOdds and write it to the database
                if (challengeE.cElo == BigDecimal("-0000.00")) {
                    updateCElo(challengeE.name, challenge.challengeElo)
                }
            }
        }
    }


    // Finds the ChallengeE with the given name and updates its cElo to newElo
    fun updateCElo(name: String, newElo: Double) {
        transaction {
            val challengeE = ChallengeE.findSingleByAndUpdate(ChallengesT.name eq name) {
                it.cElo = BigDecimal.valueOf(newElo)
            }
        }
    }

    // Create a sample Experiment and return it
    fun buildExperiment(): Experiment {
        val hlp = listOf(hang, limit, pull)
        val v = listOf(volume)

        val experiment: Experiment = Experiment(
            "livohapu0",
            Descriptions.LIVOHAPU0,
            arrayOf(hlp, v, null, hlp, v, null, null) as Array<List<Skill>>,
            4,
            arrayOf(hang20mm90max, pullup85max),
            arrayOf(isolateV7in5, projectV7in5, isolateV8in7, projectV8in7),
            arrayOf(pull2RM, hang20mm7sec1RM))
        return experiment
    }


    // Entry point into the command line interface version
    fun beginAlt(args: Array<String>) {
        // Connect to the test database
        dbFile = "testdata/testData.db"
        connectToSQLiteDB()
        // =============================================================================
        val e: Experiment = buildExperiment();
        val displayer: EDisplayer = TextEDisplayer()
        displayer.dashboardOf(e)
        // =============================================================================


    }

    fun begin() {
        // loadChallengesFromRealDatabase()
        loadChallengesFromTestDatabase()
        // loadSamBouldering()

        println("Beginning flow...")

        while (true) {
            println(TITLE_TOP)
            println(EDITOR)
            println(STATS)
            println(PLANS)
            println(OBJECTIVES)
            println(EXIT)
            println(BOTTOM)
            print(PROMPT_SYM)

            val choice = readln()

            when (choice) {
                "1" -> {
                    println("\nEDITOR")
                }

                "2" -> {
                    println("\nSTATS")
                }

                "3" -> {
                    println("\nPLANS")

                    var i = 0
                    for (p in planList) {
                        println("[${i+1}] $GREEN${p.id}$RESET")
                        i++
                    }
                    println("[b] back")

                    println("|$BAR|")
                    // println("==============================================")
                    print(": ")
                    val input: String = readln()
                    if (input == "b") {continue}

                    val type: Type = planList[input.toInt() - 1].type


                    when (type) {
                        Type.day -> {
                            val day = planList[input.toInt() - 1] as Day
                            println()
                            day.printDayPlan()
                        }

                        Type.week -> {
                            val weekPlan = planList[input.toInt() - 1] as NonDay
                            println()
                            weekPlan.printNonDayPlan()
                        }

                        // model.auxiliary.Type.month -> {}
                        // model.auxiliary.Type.year -> {}

                        else -> {}
                    }
                }

                "4" -> {
                    println(CHALLENGES_TOP)

                    var i = 1
                    for (o in objectiveList) {
                        print("[$i] ")
                        o.printShort(0)
                        i++
                    }
                    println("[b] back")

                    print(": ")
                    val input = readln()
                    if (input == "b") {continue}
                    val obj: Objective = objectiveList[input.toInt() - 1]
                    //val idInput: String = objectives[input.toInt() - 1].id
                    val nameInput: String = obj.name

                    val ob: Objective = objectiveList.find {it.name == nameInput} as Objective

                    if (ob.objectiveType == ObjectiveType.CHALLENGE || ob.objectiveType == ObjectiveType.TEST) {
                        println("\nDid you succeed? y/n")
                        print(": ")

                        val result = readln()
                        println()
                        when(result) {
                            "y" -> {  // won
                                ob.log(1.0)
                            }
                            "n" -> {  // lost
                                ob.log(0.0)
                            }
                            else -> {}
                        }
                        println()
                        val ch: Challenge = ob as Challenge
                        ch.writeToDB(dbFile)  // write changes to the specified database file
                    } else if (ob.objectiveType == ObjectiveType.TODO) {
                        TODO("should prompt for either 'Complete' or 'Error'")
                    }
                }

                "x" -> {
                    break
                }
            }
        }
    }

}