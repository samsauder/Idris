// Flow
// Description: for loading data and controlling the text interface

import sampleData.*

import model.Plan
import model.DayPlan
import model.NonDayPlan

import model.Objective
import model.auxiliary.ObjectiveType
import model.auxiliary.Type

import constants.Styles.GREEN
import constants.Styles.RESET

import constants.MenuComponents.BAR
import constants.MenuComponents.BOTTOM
import constants.MenuComponents.EXIT
import constants.MenuComponents.LOG_OBJECTIVE
import constants.MenuComponents.OBJECTIVE_EDITOR
import constants.MenuComponents.PLAN_EDITOR
import constants.MenuComponents.PROMPT_SYM
import constants.MenuComponents.TOP
import constants.MenuComponents.VIEW_PLANS


class Flow() {
    var planList: MutableList<Plan> = mutableListOf()
    var objectiveList: MutableList<Objective> = mutableListOf()


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
        loadWill() // load sample data for Will

        println("Beginning flow...")

        while (true) {
            println(TOP)
            println(PLAN_EDITOR)
            println(OBJECTIVE_EDITOR)
            println(VIEW_PLANS)
            println(LOG_OBJECTIVE)
            println(EXIT)
            println(BOTTOM)
            print(PROMPT_SYM)

            val choice = readln()

            when (choice) {
                "1" -> {
                    println("\nCREATE A PLAN")
                }

                "2" -> {
                    println("\nCREATE AN OBJECTIVE (BENCHMARK, PLAN, or TEST)")
                }

                "3" -> {
                    println("\nSELECT A PLAN")

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
                            val dayPlan = planList[input.toInt() - 1] as DayPlan
                            println()
                            dayPlan.printDayPlan()
                        }

                        Type.week -> {
                            val weekPlan = planList[input.toInt() - 1] as NonDayPlan
                            println()
                            weekPlan.printNonDayPlan()
                        }

                        // model.auxiliary.Type.month -> {}
                        // model.auxiliary.Type.year -> {}

                        else -> {}
                    }
                }

                "4" -> {
                    println("\nLOG AN OBJECTIVE")
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