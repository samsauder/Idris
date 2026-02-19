package com.idris.system.extra

// Contains descriptions for each Idris entity


object ConInfo {
    const val X_DESC = "Experiment:   a plan of action to improve a skill"
    const val F_DESC = "Foundation:   a fundamental task that improves a skill     (100% success rate)"
    const val C_DESC = "Challenge:    a difficult task that improves a skill       (50% success rate or less)"
    const val E_DESC = "Exam:         an isolated test for some aspect of a skill  (100% success rate or 0% success rate)"
    const val P_DESC = "Progression:  a sequence of related challenges in the same tier of difficulty with increasingly strict constraints"
    const val T_DESC = "Tile:         a sequence of related progressions with increasingly difficult tiers"
    const val D_DESC = "Day:          a list of tasks to improve a skill           (part of an experiment)"
    const val R_DESC = "Record:       a result log for a recently completed foundation/challenge/exam"
}