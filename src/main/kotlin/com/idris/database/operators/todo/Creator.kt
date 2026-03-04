package com.idris.database.operators.todo

import com.idris.database.entities.*
import com.idris.system.concepts.*
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Util.challengesFromNames
import com.idris.system.extra.Util.input
import com.idris.system.extra.Util.inputBool
import com.idris.system.extra.Util.inputConCor
import com.idris.system.extra.Util.inputConceptNames
import com.idris.system.extra.Util.inputInt
import com.idris.system.extra.Util.inputInts
import com.idris.system.extra.Util.inputObjCor
import com.idris.system.extra.Util.inputProgression
import com.idris.system.extra.Util.inputString
import com.idris.system.extra.Util.inputStrings


object Creator : Operator() {
    override fun f() {  // add a FoundationE to the database
        val f = inputObjCor(Foundation(), ConceptType.FOUNDATION) as Foundation
        FOUNDATIONS.insert(f)
    }

    override fun c(datapath: String) {  // add a ChallengeE to the database
        val c = inputObjCor(Challenge(), ConceptType.CHALLENGE) as Challenge
        c.progressionName = inputProgression()
        CHALLENGES.insert(c)
    }

    override fun e() {  // add an ExamE to the database
        val e = inputObjCor(Exam(), ConceptType.EXAM) as Exam
        EXAMS.insert(e)
    }

    override fun x() {
        val x = inputConCor(Experiment(), ConceptType.EXPERIMENT) as Experiment
        x.segment = inputConceptNames(ConceptType.DAY, 7).toList()
        x.segCount = inputInt("# OF SEGMENTS")!!
        EXPERIMENTS.insert(x)
    }

    override fun d() {
        val d = inputConCor(Day(), ConceptType.DAY) as Day
        d.foundationNames = inputConceptNames(ConceptType.FOUNDATION, 10)
        d.progressionNames = inputConceptNames(ConceptType.PROGRESSION, 5)
        DAYS.insert(d)
    }

    override fun p(datapath: String) {
        val p = inputConCor(Progression(), ConceptType.PROGRESSION) as Progression
        val challenges: List<String?> = inputConceptNames(ConceptType.CHALLENGE, 10).toList()
        p.challenges = challengesFromNames(challenges)
        PROGRESSIONS.insert(p)
    }

    override fun r() {
        val r = inputConCor(Record(), ConceptType.RECORD) as Record
        r.objectiveName = inputString("OBJECTIVE")
        r.won = inputBool("RESULT")!!
        r.date = inputString("DATETIME")
        RECORDS.insert(r)
    }

    fun t() {  // create a Tile
        // TODO("refactor")
        val skill = input("SKILL") as String
        val activity = input("ACTIVITY") as String
        val tiers: Array<String?> = inputStrings("TIER", 5)  // take in a sequence of tiers
        val values: Array<Int?> = inputInts("VALUE", 5)      // take in a sequence of values
        val unit = input("UNIT") as String                          // unit of the value

        for (tier in tiers) {  // for each tier
            if (tier == null) break

            val challenges: Array<String?> = arrayOfNulls(10)
            val at = "$activity$tier"
            val nameP = "$at.Y$unit"         // progression name
            val descP = "$at in Y ${unit}."  // progression description
            var i = 0  // challenge index

            for (value in values) {  // for each value
                if (value == null) break
                val nameC = "$at.$value$unit"                                            // challenge name
                val descC = "$at in $value $unit."                                       // challenge description
                val mins = if (unit == "m") value!!.toDouble() else 0.0                    // if unit is minutes

                val c = Challenge(nameC, skill, descC, mins)
                c.progressionName = nameP
                CHALLENGES.insert(c)
                challenges[i] = nameC  // add challenge name to challenges
                i++
            }

            val p = Progression(nameP, skill, descP, challenges.toList() as List<String>)
            PROGRESSIONS.insert(p)
        }
    }
}