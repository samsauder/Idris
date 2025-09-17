// Flow
// Description: for loading data and controlling the text interface

import sampleData.*

import model.Plan
import model.Day
import model.NonDay

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
import constants.MenuComponents.PLANS
import constants.MenuComponents.PROMPT_SYM
import constants.MenuComponents.STATS
import constants.MenuComponents.TOP


class Flow() {
    var planList: MutableList<Plan> = mutableListOf()
    var objectiveList: MutableList<Objective> = mutableListOf()

    fun loadSamChess() {
        chessStudyDay.objectives.addAll(chessViChallenges)  // add Visualization challenges
        chessStudyDay.objectives.addAll(chessTaChallenges)  // add Tactics challenges
        // chessStudyDay.objectives.addAll(chessOpChallenges)  // add Opening challenges
        // chessStudyDay.objectives.addAll(chessStChallenges)  // add Strategy challenges

        chessRapidDay.objectives.addAll(chessViChallenges)  // add Visualization challenges
        chessRapidDay.objectives.addAll(chessTaChallenges)  // add Tactics challenges
        chessRapidDay.objectives.addAll(chessRaChallenges)  // add Rapid challenges
        // chessRapidDay.objectives.addAll(chessAnChallenges)  // add Analysis challenges

        chessGuessDay.objectives.addAll(chessViChallenges)  // add Visualization challenges
        chessGuessDay.objectives.addAll(chessTaChallenges)  // add Tactics challenges
        chessGuessDay.objectives.addAll(chessGuChallenges)  // add Guess challenges
        // chessGuessDay.objectives.addAll(chessAnChallenges)  // add Analysis challenges

        chess7D.subplans = mutableListOf(chessStudyDay,
                                         chessRapidDay,
                                         chessRapidDay,
                                         chessStudyDay,
                                         chessRapidDay,
                                         chessRapidDay,
                                         chessGuessDay)

        objectiveList.addAll(chessViChallenges)
        objectiveList.addAll(chessTaChallenges)
        objectiveList.addAll(chessRaChallenges)
        objectiveList.addAll(chessGuChallenges)

        planList.addAll(mutableListOf(chessStudyDay, chessRapidDay, chessGuessDay, chess7D))  // load plans
    }

    // Load data into planList and objectiveList
    fun loadSam() {
        // Bouldering data
        boulderingD1Limit.objectives.addAll(boulderingPlan1Challenges)
        boulderingD1Limit.objectives.addAll(boulderingPlan1Tasks)
        boulderingD1Limit.objectives.addAll(boulderingPlan1Tests)

        boulderingD3Volume.objectives.addAll(boulderingPlan2Challenges)
        boulderingD3Volume.objectives.addAll(boulderingPlan2Tasks)
        boulderingD3Volume.objectives.addAll(boulderingPlan2Tests)

        boulderingD6Limit.objectives.addAll(boulderingPlan1Challenges)
        boulderingD6Limit.objectives.addAll(boulderingPlan1Tasks)
        boulderingD6Limit.objectives.addAll(boulderingPlan1Tests)

        boulderingWeekPlan1.subplans = mutableListOf(boulderingD1Limit, boulderingD3Volume, boulderingD6Limit)

        // load into objectiveList
        objectiveList.addAll(boulderingPlan1Challenges)
        objectiveList.addAll(boulderingPlan2Challenges)
        objectiveList.addAll(boulderingPlan1Tasks)
        objectiveList.addAll(boulderingPlan2Tasks)
        objectiveList.addAll(boulderingPlan1Tests)
        objectiveList.addAll(boulderingPlan2Tests)

        // load into planList
        planList.addAll(mutableListOf(boulderingD1Limit, boulderingD3Volume, boulderingD6Limit, boulderingWeekPlan1))
    }


    fun loadWill() {
        // load into objectiveList
        objectiveList.addAll(karateChallenges)
        objectiveList.addAll(karateTests)
    }


    fun begin() {
        // loadSam()  // load sample data for Sam
        // loadWill() // load sample data for Will
        loadSamChess()  // load sample chess data for Sam

        println("Beginning flow...")

        while (true) {
            println(TOP)
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
                    println("\nOBJECTIVES")
                    println("|$BAR|")
                    var i = 1
                    for (o in objectiveList) {
                        print("[$i] ")
                        o.printShort(0)
                        // println("[$i] $YELLOW${b.id}$RESET $RED${b.getBenchmarkEloString()}$RESET ${(b.odds * 100).toInt()}%")
                        i++
                    }
                    println("[b] back")

                    print(": ")
                    val input = readln()
                    if (input == "b") {continue}
                    val obj: Objective = objectiveList[input.toInt() - 1]
                    //val idInput: String = objectives[input.toInt() - 1].id
                    val idInput: String = obj.id

                    val ob: Objective = objectiveList.find {it.id == idInput} as Objective

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