package com.idris// com.idris.Flow
// Description: for loading data and controlling the text interface

import com.idris.constants.Descriptions
import com.idris.constants.MenuComponents
import com.idris.constants.Styles
import com.idris.database.ChallengeE
import com.idris.database.ExamE
import com.idris.database.ChallengesT
import com.idris.database.FoundationE
import com.idris.database.FoundationsT
import com.idris.database.ExamsT
import com.idris.model.Day
import com.idris.model.Exam
import com.idris.model.Foundation
import com.idris.model.NonDay
import com.idris.model.Objective
import com.idris.model.Plan
import com.idris.model.Skill
import com.idris.model.auxiliary.ObjectiveType
import com.idris.model.auxiliary.Type
import com.idris.model.challenge.Challenge

import com.idris.model.lab.Experiment;

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
import java.util.Scanner

val BAR = "======================================"

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
        // dbFile = "../../sdata/realData.db"
        dbFile = "../../testdata/testData.db"
        connectToSQLiteDB()

        // =============================================================================
        // val e: Experiment = buildExperiment();
        // val displayer: EDisplayer = TextEDisplayer()
        // displayer.dashboardOf(e)
        // =============================================================================

        var command = ""
        var option = ""
        var param = ""

        println()

        if (args.size == 0) {
            println("ERROR: no Idris command given")
        }
        if (args.size >= 1) {
            command = args[0];
        }
        if (args.size >= 2) {
            option = args[1];
        }
        if (args.size == 3) {
            param = args[2];
        }
        if (args.size > 3){
            println("ERROR: too many arguments given (1 to 3 expected)")
        }

        println()
        when(command) {
            "list" -> list(option)
            "create" -> create(option)
            "delete" -> delete(option, param)
        }
        println("\n")
    }


    // Lists all objects in the database specified by one of the following options.
    // -x | Experiment
    // -f | Foundation
    // -c | Challenge
    // -e | Exam
    fun list(option: String) {
        transaction {
            when(option) {
                "-f" -> {  // print all Foundations
                    println("             FOUNDATIONS")
                    println(BAR)
                    for (foundationEntity in FoundationE.Companion.all()) {
                        val foundation = foundationEntity.deEntify()
                        foundation.printShort(0);
                    }
                }
                "-c" -> {  // print all Challenges
                    println("             CHALLENGES")
                    println(BAR)
                    for (challengeEntity in ChallengeE.Companion.all()) {
                        val challenge = challengeEntity.toChallenge()
                        challenge.printShort(0)
                    }
                }

                "-e" -> {  // print all Exams
                    println("             EXAMS")
                    println(BAR)
                    for (examEntity in ExamE.Companion.all()) {
                        val exam = examEntity.deEntify();
                        exam.printShort(0)
                    }
                }
            }
        }
    }


    // Delete the object in the database with the given name (assume that the name is unique)
    // -x | Experiment
    // -f | Foundation
    // -c | Challenge
    // -e | Exam
    fun delete(option: String, name: String) {
        transaction {
            when(option) {
                "-f" -> {  // delete the named Foundation
                    val fIterator =  FoundationE.find { FoundationsT.name eq name }.iterator()
                    val f = fIterator.next()
                    f.delete()
                    println("\nDeleted '${f.name}' from the Foundation table.")
                }
                "-c" -> {  // delete the named Challenge
                    val cIterator =  ChallengeE.find { ChallengesT.name eq name }.iterator()
                    val c = cIterator.next()
                    c.delete()
                    println("\nDeleted '${c.name}' from the Challenge table.")
                }

                "-e" -> {
                    val eIterator =  ExamE.find { ExamsT.name eq name }.iterator()
                    val e = eIterator.next()
                    e.delete()
                    println("\nDeleted '${e.name}' from the Exam table.")
                }

            }
        }
    }


    // Adds an object to the database specified by one of the following options.
    // -x | Experiment
    // -f | Foundation
    // -c | Challenge
    // -e | Exam
    fun create(option: String) {
        when (option) {
            "-f" -> createFoundation()
            "-c" -> createChallenge()
            "-e" -> createExam()
        }
    }

    
    // Makes a Foundation from standard input and adds it to the database
    fun createFoundation() {
        val f = Foundation("",null,"",-1.0)
        this.fillObjectiveCore(f)

        val skillName = f.skill?.id

        transaction {
            FoundationE.new {
                name = f.name
                if (skillName != null) skill = skillName;
                description = f.description

                minutes = f.minutes.toBigDecimal()
            }
            println("\nAdded '${f.name}' to the Foundation table.")
        }
    }


    fun createExam() {
        val e  = Exam("", null, "", -1.0, false)
        this.fillObjectiveCore(e)

        val skillName = e.skill?.id

        transaction {
            ExamE.new {
                name = e.name
                if (skillName != null) skill = skillName;
                description = e.description

                minutes = e.minutes.toBigDecimal()
                passed = false
            }
            println("\nAdded '${e.name}' to the Exam table.")
        }
    }



    fun createChallenge() {
        val c = Challenge("",null,"",-1.0, 0.01)
        this.fillObjectiveCore(c)
        val s = Scanner(System.`in`)

        print("ODDS  ")
        c.userOdds = s.nextDouble()
        c.generateChallengeElo()

        val skillName = c.skill?.id

        transaction {
            ChallengeE.new {
                name = c.name
                if (skillName != null) skill = skillName;
                description = c.description

                minutes = c.minutes.toBigDecimal()
                cElo = c.challengeElo.toBigDecimal()
                uElo = c.userElo.toBigDecimal()
                uOdds = c.userOdds.toBigDecimal()
            }
            println("\nAdded '${c.name}' to the Challenge table.")
        }
    }

    
    // Return a filled Objective object storing common attributes for Foundations and Challenges (stdin)
    fun fillObjectiveCore(emptyObj: Objective): Objective {
        val s = Scanner(System.`in`)
        
        print("NAME  ")
        emptyObj.name = s.next()
        
        print("SKILL  ")
        emptyObj.skill = Skill(s.next(), null)
        
        print("DESCRIPTION  ")
        val z = Scanner(System.`in`)
        emptyObj.description = z.nextLine()

        print("MINUTES  ")
        val w = Scanner(System.`in`)
        emptyObj.minutes = z.nextDouble()

        return emptyObj
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

                    if (ob.objectiveType == ObjectiveType.CHALLENGE || ob.objectiveType == ObjectiveType.EXAM) {
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
                    } else if (ob.objectiveType == ObjectiveType.FOUNDATION) {
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