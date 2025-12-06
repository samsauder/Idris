package com.idris// com.idris.Flow
// Description: for loading data and controlling the text interface

import com.idris.constants.Descriptions
import com.idris.constants.MenuComponents
import com.idris.constants.Styles
import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.model.Day
import com.idris.model.NonDay
import com.idris.model.Objective
import com.idris.model.Plan
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType
import com.idris.model.auxiliary.Type
import com.idris.model.challenge.Challenge

import com.idris.model.lab.Experiment;

import com.idris.model.lab.EDisplayer
import com.idris.model.lab.TextEDisplayer
import com.idris.sampleData.boulderingD1Limit
import com.idris.sampleData.boulderingD2Volume
import com.idris.sampleData.boulderingD4Limit
import com.idris.sampleData.boulderingD5Volume
import com.idris.sampleData.boulderingLiChallenges
import com.idris.sampleData.boulderingLiTodos
import com.idris.sampleData.boulderingVoChallenges
import com.idris.sampleData.boulderingWeek1a
import com.idris.sampleData.chess7D
import com.idris.sampleData.chessGuessDay
import com.idris.sampleData.chessRapidDay
import com.idris.sampleData.chessStudyDay
import com.idris.sampleData.chessTaChallenges
import com.idris.sampleData.hang
import com.idris.sampleData.hang20mm7sec1RM
import com.idris.sampleData.hang20mm90max
import com.idris.sampleData.isolateV7in5
import com.idris.sampleData.isolateV8in7
import com.idris.sampleData.karateChallenges
import com.idris.sampleData.karateTests
import com.idris.sampleData.limit
import com.idris.sampleData.new3x3Reds
import com.idris.sampleData.new4x3Reds
import com.idris.sampleData.new5x3Reds
import com.idris.sampleData.new7Reds120
import com.idris.sampleData.newPurple
import com.idris.sampleData.projectBlack
import com.idris.sampleData.projectV7in5
import com.idris.sampleData.projectV8in7
import com.idris.sampleData.pull
import com.idris.sampleData.pull2RM
import com.idris.sampleData.pullup85max
import com.idris.sampleData.volume
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.sql.Connection

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

        chess7D.subplans = mutableListOf(
            chessStudyDay,
            chessRapidDay,
            chessRapidDay,
            chessStudyDay,
            chessRapidDay,
            chessRapidDay,
            chessGuessDay
        )

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


        boulderingWeek1a.subplans = mutableListOf(
            boulderingD1Limit,
            boulderingD2Volume,
            boulderingD4Limit,
            boulderingD5Volume
        )

        // load into objectiveList
        objectiveList.addAll(boulderingLiTodos)
        objectiveList.addAll(boulderingLiChallenges)
        objectiveList.addAll(boulderingVoChallenges)

        // load into planList
        planList.addAll(mutableListOf(
            boulderingD1Limit,
            boulderingD2Volume,
            boulderingD4Limit,
            boulderingD5Volume,
            boulderingWeek1a
        ))
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
            for (challengeE in ChallengeE.Companion.all()) {
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
            for (challengeE in ChallengeE.Companion.all()) {
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
            val challengeE = ChallengeE.Companion.findSingleByAndUpdate(ChallengesT.name eq name) {
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
            arrayOf(pull2RM, hang20mm7sec1RM)
        )
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
            println(MenuComponents.TITLE_TOP)
            println(MenuComponents.EDITOR)
            println(MenuComponents.STATS)
            println(MenuComponents.PLANS)
            println(MenuComponents.OBJECTIVES)
            println(MenuComponents.EXIT)
            println(MenuComponents.BOTTOM)
            print(MenuComponents.PROMPT_SYM)

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
                        println("[${i+1}] ${Styles.GREEN}${p.id}${Styles.RESET}")
                        i++
                    }
                    println("[b] back")

                    println("|${MenuComponents.BAR}|")
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

                        // com.idris.Type.month -> {}
                        // com.idris.Type.year -> {}

                        else -> {}
                    }
                }

                "4" -> {
                    println(MenuComponents.CHALLENGES_TOP)

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