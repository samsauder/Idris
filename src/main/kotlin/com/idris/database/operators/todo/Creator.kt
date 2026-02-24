package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.DAYS
import com.idris.database.entities.EXAMS
import com.idris.database.entities.EXPERIMENTS
import com.idris.database.entities.FOUNDATIONS
import com.idris.database.entities.PROGRESSIONS
import com.idris.database.entities.RECORDS
import com.idris.system.concepts.*
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import com.idris.system.extra.Util.challengesFromNames
import com.idris.system.extra.Util.input
import com.idris.system.extra.Util.inputBool
import com.idris.system.extra.Util.inputConceptCore
import com.idris.system.extra.Util.inputConceptNames
import com.idris.system.extra.Util.inputInt
import com.idris.system.extra.Util.inputInts
import com.idris.system.extra.Util.inputObjectiveCore
import com.idris.system.extra.Util.inputProgression
import com.idris.system.extra.Util.inputString
import com.idris.system.extra.Util.inputStrings


object Creator : Operator() {
    override fun f() {  // add a FoundationE to the database
        val f = inputObjectiveCore(Foundation(), ConceptType.FOUNDATION) as Foundation
        FOUNDATIONS.insert(f)
        message(f.name, ConceptType.FOUNDATION)
    }

    override fun c(datapath: String) {  // add a ChallengeE to the database
        val c = inputObjectiveCore(Challenge(), ConceptType.CHALLENGE) as Challenge
        c.progressionName = inputProgression()
        CHALLENGES.insert(c)
        message(c.name, ConceptType.CHALLENGE)
    }

    override fun e() {  // add an ExamE to the database
        val e = inputObjectiveCore(Exam(), ConceptType.EXAM) as Exam
        EXAMS.insert(e)
        message(e.name, ConceptType.EXAM)
    }

    override fun x() {
        val x = inputConceptCore(Experiment(), ConceptType.EXPERIMENT) as Experiment
        x.segment = inputConceptNames(ConceptType.DAY, 7).toList()
        x.segCount = inputInt("# OF SEGMENTS")!!
        EXPERIMENTS.insert(x)
        message(x.name, ConceptType.EXPERIMENT)
    }

    override fun d() {
        val d = inputConceptCore(Day(), ConceptType.DAY) as Day
        d.foundationNames = inputConceptNames(ConceptType.FOUNDATION, 10) as Array<String>
        d.progressionNames = inputConceptNames(ConceptType.PROGRESSION, 5) as Array<String>
        DAYS.insert(d)
        message(d.name, ConceptType.DAY)
    }

    override fun p(datapath: String) {
        val p = inputConceptCore(Progression(), ConceptType.PROGRESSION) as Progression
        val challenges: List<String?> = inputConceptNames(ConceptType.CHALLENGE, 10).toList()
        p.challenges = challengesFromNames(challenges)
        PROGRESSIONS.insert(p)
        message(p.name, ConceptType.PROGRESSION)
    }

    fun r() {
        val r = inputConceptCore(Record(), ConceptType.RECORD) as Record
        r.objectiveName = inputString("OBJECTIVE")
        r.won = inputBool("RESULT")!!
        r.date = inputString("DATETIME")
        RECORDS.insert(r)
        message(r.name, ConceptType.RECORD)
    }

    fun t() {  // create a Tile
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
                val mins = if (unit == "m") value.toDouble() else 0.0                    // if unit is minutes

                val c = Challenge(nameC, skill, descC, mins)
                CHALLENGES.insert(c)
                challenges[i] = nameC  // add challenge name to challenges
                i++
            }

            val p = Progression(nameP, skill, descP, challenges.toList() as List<String>)
            PROGRESSIONS.insert(p)
        }
    }

    // =======================================================================================================

    // What is displayed after an entity with the given name is added to the database
    fun message(name: String, ct: ConceptType) {
        println("\nAdded ${style(name, Styles.BOLD)} to the $ct table.")
    }
}