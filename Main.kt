// Main

fun main() {
    /*
    // Bouldering
    // --------------------------------------------------------------------------------------------
    val bouldering = Skill("bouldering", null)
    val limit = Skill("limit", null)
    val volume = Skill("volume", null)
    val hang = Skill("hang", null)
    val pull = Skill("pull", null)
    val core = Skill("core", null)
    val antagonist = Skill("antagonist", null)

    val hang20mm90max = Benchmark("hang20mm90max", hang, 20, 1.0, "Complete 6 sets of 1 rep of 90% 1RM weighted hangs at 20mm (2m rests).")
    val pullup85max = Benchmark("pullup85max", pull, 20, 1.0, "Complete 5 sets of 3 reps of 85% 2RM weighted pullups (3m rests)")

    val newV7 = Benchmark("newV7", limit, 60,0.25, "Complete a never before seen V7 in 1 60-min session.")
    val projectV7 = Benchmark("projectV7", limit, 480, 0.75, "Complete a never before seen V7 over a max of 4 60-min sessions.")
    val projectV8 = Benchmark("projectV8", limit, 480, 0.01, "Complete a never before seen V8 over a max of 4 60-min sessions.")

    val threeV5nineV6 = Benchmark("threeV5nineV6", volume, 90, 0.25, "Complete 3 V5s and 9 V6s in 1 90-min session")

    val hangingKneeRaises = Benchmark("hangingKneeRaises", core, 10, 1.0, "Complete 3 sets of 10 reps of hanging knee raises to chest.")
    val bentLegWindshieldWipers = Benchmark("bentLegWindshieldWipers", core,10, 1.0, "Complete 3 sets of 10 reps (alternating sides) of bent leg windshield wipers. ")

    val benchpress = Benchmark("benchpress", antagonist, 30, 1.0, "Complete 3 sets of 5 reps of bench press (5min rests).")

    val boulderingD1Plan1 = DayPlan("boulderingD1Plan1", bouldering, mutableListOf(hang, limit, pull), "2025-09-01")
    val boulderingD3Plan2 = DayPlan("boulderingD3Plan2", bouldering, mutableListOf(volume, core, antagonist), "2025-09-03")
    val boulderingD6Plan1 = DayPlan("boulderingD6Plan1", bouldering, mutableListOf(hang, limit, pull), "2025-09-06")

    val boulderingPlan1Benchmarks = mutableListOf(hang20mm90max, newV7, pullup85max)
    val boulderingPlan2Benchmarks = mutableListOf(threeV5nineV6, hangingKneeRaises, bentLegWindshieldWipers, benchpress)

    boulderingD1Plan1.benchmarks = boulderingPlan1Benchmarks
    boulderingD3Plan2.benchmarks = boulderingPlan2Benchmarks
    boulderingD6Plan1.benchmarks = boulderingPlan1Benchmarks

    val boulderingWeekPlan1 = NonDayPlan("boulderingWeekPlan",
                                              Type.week,
                                        bouldering,
                                    mutableListOf(limit, volume, hang, pull, core, antagonist),
                                   "2025-09-01")

    boulderingWeekPlan1.subplans = mutableListOf(boulderingD1Plan1, boulderingD3Plan2, boulderingD6Plan1)
    // ---------------------------------------------------------------------------------------------


    // Chess
    // ---------------------------------------------------------------------------------------------
    val rapid = Skill("rapid", null)
    val analysis = Skill("analysis", null)
    val tactics = Skill("tactics", null)
    val chess = Skill("chess", mutableListOf(rapid, analysis, tactics))

    val rapid2200 = Benchmark("rapid2200", rapid, 45,0.25, "Beat a 2200 Rapid player on Lichess.")
    val storm55 = Benchmark("storm55", tactics, 3, 0.10, "Score 55 on Puzzle Storm.")
    val storm50 = Benchmark("storm50", tactics, 3, 0.4, "Score 50 on Puzzle Storm.")

    val gtm = Skill("gtm", null)
    val gtm8pt6 = Benchmark("gtm8.6", gtm, 90,0.25, "Achieve an 8.6+ average on Guess the Move.")

    val chessD1Plan1 = DayPlan("chessD1Plan1", chess, mutableListOf(rapid, tactics), "2025-09-01")
    val chessD2Plan2 = DayPlan("chessD2Plan2", chess, mutableListOf(gtm, tactics), "2025-09-02")
    val chessD3Plan1 = DayPlan("chessD3Plan1", chess, mutableListOf(rapid, tactics), "2025-09-03")
    val chessD4Plan2 = DayPlan("chessD4Plan2", chess, mutableListOf(gtm, tactics), "2025-09-04")
    val chessD5Plan1 = DayPlan("chessD5Plan1", chess, mutableListOf(rapid, tactics), "2025-09-05")
    val chessD6Plan2 = DayPlan("chessD6Plan2", chess, mutableListOf(gtm, tactics), "2025-09-06")
    val chessD7Plan1 = DayPlan("chessD7Plan1", chess, mutableListOf(rapid, tactics), "2025-09-07")

    val plan1Benchmarks = mutableListOf(rapid2200, storm50, storm55)
    val plan2Benchmarks = mutableListOf(gtm8pt6, storm50, storm55)

    chessD1Plan1.benchmarks = plan1Benchmarks
    chessD2Plan2.benchmarks = plan2Benchmarks
    chessD3Plan1.benchmarks = plan1Benchmarks
    chessD4Plan2.benchmarks = plan2Benchmarks
    chessD5Plan1.benchmarks = plan1Benchmarks
    chessD6Plan2.benchmarks = plan2Benchmarks
    chessD7Plan1.benchmarks = plan1Benchmarks

    val chessW1Plan1 = NonDayPlan("chessWeekPlan1", Type.week, chess, chess.subskills, "2025-09-01")

    chessW1Plan1.subplans =
        mutableListOf(chessD1Plan1,
                      chessD2Plan2,
                      chessD3Plan1,
                      chessD4Plan2,
                      chessD5Plan1,
                      chessD6Plan2,
                      chessD7Plan1)
    // ---------------------------------------------------------------------------------------------


    // Leetcode
    // ---------------------------------------------------------------------------------------------
    val leetcode = Skill("leetcode", null)
    val leetcodeMed20Min = Benchmark("leetcodeMed20Min", leetcode, 20,0.25, "Find an optimal solution to a Leetcode Medium in under 20 mins.")
    val leetCodeHard60Min = Benchmark("leetcodeHard60Min", leetcode, 60,0.25, "Find an optimal solution to a Leetcode Hard in under 60 mins.")
    val leetcodeDayPlan1 = DayPlan("leetcodeDayPlan1", leetcode, null,"2025-08-12")
    leetcodeDayPlan1.benchmarks = mutableListOf(leetcodeMed20Min, leetCodeHard60Min)


    // Occult
    // ---------------------------------------------------------------------------------------------
    val occult = Skill("occult", null)
    val thothTarot1Card5Min = Benchmark("thothTarot1Card5Min", occult, 5,0.10, "Complete a comprehensive 1-card thoth tarot reading in under 5 mins.")
    val thothTarot3Card15Min = Benchmark("thothTarot3Card15Min", occult, 15, 0.10, "Complete a comprehensive 3-card thoth tarot reading in under 15 mins.")

    val occultDayPlan1 = DayPlan("occultDayPlan1", occult,null, "2025-08-12")
    occultDayPlan1.benchmarks = mutableListOf(thothTarot1Card5Min, thothTarot3Card15Min)
    // ---------------------------------------------------------------------------------------------


    // Printing plans
    // ---------------------------------------------------------------------------------------------

    /*

    // Bouldering
    boulderingD1Plan1.printDayPlan()
    boulderingD3Plan2.printDayPlan()
    boulderingD6Plan1.printDayPlan()
    boulderingWeekPlan1.printNonDayPlan()

    // Chess
    chessD1Plan1.printDayPlan()
    chessD2Plan2.printDayPlan()
    chessW1Plan1.printNonDayPlan()

    // Leetcode
    leetcodeDayPlan1.printDayPlan()

    // Occult
    occultDayPlan1.printDayPlan()

    */

    // ---------------------------------------------------------------------------------------------

    */
    val f = Flow()
    f.begin()  // Start prompting the user for commands
}