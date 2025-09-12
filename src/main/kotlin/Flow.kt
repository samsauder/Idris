// Flow  for controlling the text UI
import StyleConstants.GREEN
import StyleConstants.YELLOW
import StyleConstants.RED
import StyleConstants.BLUE
import StyleConstants.BOLD
import StyleConstants.RESET

class Flow() {
    fun begin() {
        // Bouldering
        // --------------------------------------------------------------------------------------------
        val bouldering = Skill("bouldering", null)
        val limit = Skill("limit", null)
        val volume = Skill("volume", null)
        val hang = Skill("hang", null)
        val pull = Skill("pull", null)
        val core = Skill("core", null)
        val antagonist = Skill("antagonist", null)

        val boulderingD1Limit = DayPlan("boulderingD1Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-01")
        val boulderingD3Volume = DayPlan("boulderingD3Volume", bouldering, mutableListOf(volume, core, antagonist), "2025-09-03")
        val boulderingD6Limit = DayPlan("boulderingD6Limit", bouldering, mutableListOf(hang, limit, pull), "2025-09-06")

        val newV7 = Benchmark("newV7", limit,"Complete a never before seen V7 within 1 60-min session.",15, 0.25)
        val projectV7 = Benchmark("projectV7", limit,"Complete a never before seen V7 within 4 60-min sessions.",15, 0.60)
        val projectV8 = Benchmark("projectV8", limit, "Complete a never before seen V8 within 4 60-min sessions.",45,0.05)
        val threeV5nineV6 = Benchmark("threeV5nineV6", volume,"Complete 3 V5s and 9 V6s within 1 90-min session.",90, 0.10)

        val pullup85max = Task("pullup85max", pull,"Complete 5 sets of 3 reps of 85% 2RM weighted pullups (3m rests).",20)
        val hangingKneeRaises = Task("hangingKneeRaises", core, "Complete 3 sets of 10 reps of hanging knee raises to chest.",10)
        val bentLegWindshieldWipers = Task("bentLegWindshieldWipers", core, "Complete 3 sets of 10 reps (alternating sides) of bent leg windshield wipers. ", 10)
        val benchpress = Task("benchpress", antagonist,"Complete 3 sets of 5 reps of bench press (5min rests).", 30)
        val hang20mm90max = Task("hang20mm90max", hang, "Complete 6 sets of 1 rep of 90% 1RM weighted hangs at 20mm (2m rests).", 20)

        val pullup2RMplus50lb = Test("plus50pullup2RM", pull, "Achieve a +50 lb 2RM in weighted pullups.", 1)
        val bench1RM80lb = Test("bench1RM80lb", antagonist, "Achieve an 80 lb 1RM in bench press.", 1)

        val boulderingPlan1Benchmarks = mutableListOf(projectV8, newV7)
        val boulderingPlan1Tasks = mutableListOf(hang20mm90max, pullup85max)
        val boulderingPlan1Tests = mutableListOf(pullup2RMplus50lb)

        val boulderingPlan2Benchmarks = mutableListOf(threeV5nineV6)
        val boulderingPlan2Tasks = mutableListOf(hangingKneeRaises, bentLegWindshieldWipers, benchpress)
        val boulderingPlan2Tests = mutableListOf(bench1RM80lb)  // current is 75 lb

        boulderingD1Limit.objectives.addAll(boulderingPlan1Benchmarks)
        boulderingD1Limit.objectives.addAll(boulderingPlan1Tasks)
        boulderingD1Limit.objectives.addAll(boulderingPlan1Tests)

        boulderingD3Volume.objectives.addAll(boulderingPlan2Benchmarks)
        boulderingD3Volume.objectives.addAll(boulderingPlan2Tasks)
        boulderingD3Volume.objectives.addAll(boulderingPlan2Tests)

        boulderingD6Limit.objectives.addAll(boulderingPlan1Benchmarks)
        boulderingD6Limit.objectives.addAll(boulderingPlan1Tasks)
        boulderingD6Limit.objectives.addAll(boulderingPlan1Tests)

        //boulderingD1Plan1.printDayPlan()
        //boulderingD3Plan2.printDayPlan()
        //boulderingD6Plan1.printDayPlan()

        val boulderingWeekPlan1 = NonDayPlan(
            "boulderingWeekPlan",
            Type.week,
            bouldering,
            mutableListOf(limit, volume, hang, pull, core, antagonist),
            "2025-09-01"
        )

        boulderingWeekPlan1.subplans = mutableListOf(boulderingD1Limit, boulderingD3Volume, boulderingD6Limit)
        //boulderingWeekPlan1.printNonDayPlan()

        val plans: MutableList<Plan> = mutableListOf(boulderingD1Limit, boulderingD3Volume, boulderingD6Limit, boulderingWeekPlan1)

        val objectives: MutableList<Objective> = mutableListOf()

        objectives.addAll(boulderingPlan1Benchmarks)
        objectives.addAll(boulderingPlan2Benchmarks)

        objectives.addAll(boulderingPlan1Tasks)
        objectives.addAll(boulderingPlan2Tasks)

        objectives.addAll(boulderingPlan1Tests)
        objectives.addAll(boulderingPlan2Tests)
        // ---------------------------------------------------------------------------------------------

        println("Beginning flow...")

        val width = 60
        val title: String = "SYSTEM"
        val bufferSize: Int = 2

    // val sym = "/"
        val sym = "/"
    val bar = sym.repeat(width)
    val bar2 = sym.repeat((width - title.length - bufferSize) / 2) // = (width - titlesize - titlebuffer) / 2

    while (true) {
        // get
        // println(bar2)
        println()
        println("|$bar2${BOLD} $title $RESET$bar2|")
        //println(bar3)
        println(" [${GREEN}1$RESET] ${GREEN}PLAN$RESET editor")
        println(" [${YELLOW}2$RESET] ${YELLOW}OBJECTIVE$RESET editor")
        println(" [${RED}3$RESET] ${RED}view$RESET PLAN ${RED}$RESET")
        //println(" [${RED}${BOLD}4$RESET] ${RED}view$RESET BENCHMARKS")

        println(" [${BLUE}4$RESET] ${BLUE}log$RESET OBJECTIVE")
        println(" [x] exit")
        println("|$bar|")
        // println("==============================================")
        print(": ")
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
                for (p in plans) {
                    println("[${i+1}] $GREEN${p.id}$RESET")
                    i++
                }
                println("[b] back")

                println("|$bar|")
                // println("==============================================")
                print(": ")
                val input: String = readln()
                if (input == "b") {continue}

                val type: Type = plans[input.toInt() - 1].type


                when (type) {
                    Type.day -> {
                        val dayPlan = plans[input.toInt() - 1] as DayPlan
                        println()
                        dayPlan.printDayPlan()
                    }

                    Type.week -> {
                        val weekPlan = plans[input.toInt() - 1] as NonDayPlan
                        println()
                        weekPlan.printNonDayPlan()
                    }
                    //Type.month -> {}
                    //Type.year -> {}
                    else -> {}
                }
            }

            "4" -> {
                println("\nLOGGING AN OBJECTIVE")
                println("|$bar|")
                var i = 1
                for (o in objectives) {
                    print("[$i] ")
                    o.printShort(0)
                    // println("[$i] $YELLOW${b.id}$RESET $RED${b.getBenchmarkEloString()}$RESET ${(b.odds * 100).toInt()}%")
                    i++
                }
                println("[b] back")

                print(": ")
                val input = readln()
                if (input == "b") {continue}
                val obj: Objective = objectives[input.toInt() - 1]
                //val idInput: String = objectives[input.toInt() - 1].id
                val idInput: String = obj.id

                val ob: Objective = objectives.find {it.id == idInput} as Objective

                if (ob.objectiveType == ObjectiveType.BENCHMARK || ob.objectiveType == ObjectiveType.TEST) {
                    println("\nDid you succeed? y/n")
                    // 4println("|$bar|")
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
                } else if (ob.objectiveType == ObjectiveType.TASK) {
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