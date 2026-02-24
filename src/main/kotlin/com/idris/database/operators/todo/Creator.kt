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
import com.idris.system.extra.Util.inputName
import com.idris.system.extra.Util.inputObjectiveCore
import com.idris.system.extra.Util.inputProgression
import com.idris.system.extra.Util.inputString
import com.idris.system.extra.Util.inputStrings


object Creator : Operator() {
    override fun f() {  // add a FoundationE to the database
        /* // TODO remove comment
        transaction {
            FOUNDATION.new {
                name = inputName(ConceptType.FOUNDATION, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")
                minutes = inputBigDecimal("MINUTES")
                println("\nAdded ${style(name, Styles.BOLD)} to the Foundation table.")
            }
        } */

        /*
        //val name = inputName(ConceptType.CHALLENGE, ConceptState.ABSENT)
        //val skill: String = inputSkill()
        //val description = inputString("DESCRIPTION")
        //val minutes = inputBigDecimal("MINUTES").toDouble()
         */

        val f = inputObjectiveCore(Foundation(), ConceptType.FOUNDATION) as Foundation

        FOUNDATIONS.insert(f)
        message(f.name, ConceptType.FOUNDATION)
    }


    override fun c(datapath: String) {  // add a ChallengeE to the database
        /* // TODO remove comment
        transaction {
            CHALLENGE.new {
                name = inputName(ConceptType.CHALLENGE, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")
                minutes = inputBigDecimal("MINUTES")
                progressionName = inputProgression()
                cElo = 0.0.toBigDecimal()
                uElo = 0.0.toBigDecimal()
                uOdds = 1.0.toBigDecimal()
                attempts = 0
                wins = 0
                println("\nAdded ${style(name, Styles.BOLD)} to the Challenge table.")
            }
        }*/

        /*
        // val name = inputName(ConceptType.CHALLENGE, ConceptState.ABSENT)
        // val skill: String = inputSkill()
        // val description = inputString("DESCRIPTION")
        // val minutes = inputBigDecimal("MINUTES").toDouble()
         */

        val c = inputObjectiveCore(Challenge(), ConceptType.CHALLENGE) as Challenge
        c.progressionName = inputProgression()

        CHALLENGES.insert(c)
        message(c.name, ConceptType.CHALLENGE)
    }


    override fun e() {  // add an ExamE to the database
        /* TODO remove comment
        transaction {
            EXAM.new {
                name = inputName(ConceptType.EXAM, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")
                minutes = inputBigDecimal("MINUTES")
                passed = false
                println("\nAdded ${style(name, Styles.BOLD)} to the Exam table.")
            }
        }*/

        /*
        //val name = inputName(ConceptType.CHALLENGE, ConceptState.ABSENT)
        //val skill: String = inputSkill()
        //val description = inputString("DESCRIPTION")
        //val minutes = inputBigDecimal("MINUTES").toDouble()
         */

        val e = inputObjectiveCore(Exam(), ConceptType.EXAM) as Exam
        // e.passed = inputBool("RESULT")!!  TODO delete comment

        EXAMS.insert(e)
        message(e.name, ConceptType.EXAM)
    }


    override fun x() {
        /* TODO remove comment
        Scanner(System.`in`)
        transaction {
            EXPERIMENT.new {
                name = inputName(ConceptType.EXPERIMENT, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")

                val dnms = inputConceptNames(ConceptType.DAY, 7)  // Day names

                d1 = dnms[0]!!
                d2 = dnms[1]!!
                d3 = dnms[2]!!
                d4 = dnms[3]!!
                d5 = dnms[4]!!
                d6 = dnms[5]!!
                d7 = dnms[6]!!
                segCount = inputInt("SEGMENT COUNT")!!
                println("\nAdded ${style(name, Styles.BOLD)} to the Experiment table.")
            }
        }
        */

        /*
        // val name = inputName(ConceptType.CHALLENGE, ConceptState.ABSENT)
        // val skill: String = inputSkill()
        // val description = inputString("DESCRIPTION")
        */

        val x = inputConceptCore(Experiment(), ConceptType.EXPERIMENT) as Experiment
        x.segment = inputConceptNames(ConceptType.DAY, 7).toList()
        x.segCount = inputInt("# OF SEGMENTS")!!

        EXPERIMENTS.insert(x)
        message(x.name, ConceptType.EXPERIMENT)
    }


    override fun d() {
        /* TODO remove comment
        transaction {
            DAY.new {
                name = inputName(ConceptType.DAY, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputString("DESCRIPTION")

                val fnms = inputConceptNames(ConceptType.FOUNDATION, 10)  // Foundation names
                val pnms = inputConceptNames(ConceptType.PROGRESSION, 5)  // Progression names

                f0 = fnms[0]!!
                f1 = fnms[1]!!
                f2 = fnms[2]!!
                f3 = fnms[3]!!
                f4 = fnms[4]!!
                f5 = fnms[5]!!
                f6 = fnms[6]!!
                f7 = fnms[7]!!
                f8 = fnms[8]!!
                f9 = fnms[9]!!

                p0 = pnms[0]!!
                p1 = pnms[1]!!
                p2 = pnms[2]!!
                p3 = pnms[3]!!
                p4 = pnms[4]!!
                println("\nAdded ${style(name, Styles.BOLD)} to the Day table.")
            }
        }
        */

        val d = inputConceptCore(Day(), ConceptType.DAY) as Day
        d.foundationNames = inputConceptNames(ConceptType.FOUNDATION, 10) as Array<String>
        d.progressionNames = inputConceptNames(ConceptType.PROGRESSION, 5) as Array<String>

        DAYS.insert(d)
        message(d.name, ConceptType.DAY)
    }


    override fun p(datapath: String) {
        /*
        transaction {
            PROGRESSION.new {
                name = inputName(ConceptType.PROGRESSION, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputDescription()
                c0 = inputChallenge(0)
                c1 = inputChallenge(1)
                c2 = inputChallenge(2)
                c3 = inputChallenge(3)
                c4 = inputChallenge(4)
                c5 = inputChallenge(5)
                c6 = inputChallenge(6)
                c7 = inputChallenge(7)
                c8 = inputChallenge(8)
                c9 = inputChallenge(9)
                println("\nAdded ${style(name, Styles.BOLD)} to the Progression table.")
            }
        }*/

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

    /* TODO remove comment
    fun r(recordName: String,
          skillName: String,
          description: String,
          objectiveName: String,
          won: Boolean,
          datetime: String) {  // create RECORD
        /*
        transaction {
            RECORD.new {
                this.name = recordName           // custom tag
                this.skillName = skillName      // associated skill
                this.description = description    // description
                this.objectiveName = objectiveName  // name of completed objective
                this.won = won             // success/failure
                this.date = datetime           // datetime completed
            }
        }
        */

        val r = inputConceptCore(Record()) as Record
        r.objectiveName = inputString("OBJECTIVE")
        r.won = inputBool("RESULT")!!
        r.date = inputString("DATETIME")

        // TODO RECORDS.insert(r)
        message(r.name, ConceptType.RECORD)
    }*/


    // Create 2D progression (also known as a tile)
    fun t() {
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

                // CHALLENGES.insert(nameC, nameP, skill, descC, mins)  // insert new CHALLENGE into CHALLENGES TODO remove comment
                val c = Challenge(nameC, skill, descC, mins)
                CHALLENGES.insert(c)

                challenges[i] = nameC  // add challenge name to challenges
                i++
            }

            // PROGRESSIONS.insert(nameP, skill, descP, challenges)  // insert a PROGRESSION into PROGRESSIONS TODO remove comment
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