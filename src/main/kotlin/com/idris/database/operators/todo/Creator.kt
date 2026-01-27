package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.DAY
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXPERIMENT
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import com.idris.system.concepts.Challenge
import com.idris.system.concepts.Exam
import com.idris.system.concepts.Foundation
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Styler.style
import com.idris.system.extra.Styles
import com.idris.system.extra.Util
import com.idris.system.extra.Util.inputChallenge
import com.idris.system.extra.Util.inputConceptNames
import com.idris.system.extra.Util.inputDescription
import com.idris.system.extra.Util.inputName
import com.idris.system.extra.Util.inputProgression
import com.idris.system.extra.Util.inputSkill
import com.idris.system.extra.Util.inputString
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Scanner

object Creator : Operator() {
    // ======================================================================
    override fun f() {  // add a FoundationE to the database
        transaction {
            FOUNDATION.new {
                name = inputName(ConceptType.FOUNDATION, ConceptState.ABSENT)
                skillName = inputSkill()
                description = inputDescription()
                minutes = Util.inputMinutes().toBigDecimal()
                println("\nAdded ${style(name, Styles.BOLD)} to the Foundation table.")
            }
        }
    }
    // ======================================================================
    override fun c(datapath: String) {  // add a ChallengeE to the database
        val c = Challenge("", "", "", -1.0, 0.01)
        Util.fillObjectiveCore(c, ConceptType.CHALLENGE)
        val sN = c.skillName

        val progressionNameIn = inputProgression()

        val s = Scanner(System.`in`)
        print("ODDS  ")
        c.userOdds = s.nextDouble()
        c.generateChallengeElo()

        transaction {
            CHALLENGE.Companion.new {
                name = c.name
                // skill = (c.skill?.id ?: String) as String
                if (skillName != "") skillName = sN;
                description = c.description
                minutes = c.minutes.toBigDecimal()
                cElo = c.challengeElo.toBigDecimal()
                uElo = c.userElo.toBigDecimal()
                uOdds = c.userOdds.toBigDecimal()
                attempts = 0
                wins = 0
                progressionName = progressionNameIn
            }
        }

        println("\nAdded '${c.name}' to the Challenge table.")
    }
    // ======================================================================
    override fun e() {  // add an ExamE to the database
        val e  = Exam(
            "",
            "",
            "",
            -1.0,
            false
        )
        Util.fillObjectiveCore(e, ConceptType.EXAM)
        val sN = e.skillName

        transaction {
            EXAM.Companion.new {
                name = e.name
                if (skillName != "") skillName = sN;
                description = e.description

                minutes = e.minutes.toBigDecimal()
                passed = false
            }
        }

        println("\nAdded '${e.name}' to the Exam table.")
    }
    // ======================================================================
    override fun x() {  // PROVISIONALLY DONE
        // TODO("Not yet implemented")
        val nm = inputName(ConceptType.EXPERIMENT, ConceptState.ABSENT)                                // name
        val sk = inputSkill()
        val desc = inputString("DESCRIPTION")

        println("Input all Day names (X for empty, _ for rest):")
        val dnms = inputConceptNames(ConceptType.DAY, 7)

        val s = Scanner(System.`in`)
        print("SEGMENT COUNT")
        val scount = s.nextInt()

        transaction {
            EXPERIMENT.Companion.new {
                name = nm
                skillName = sk
                description = desc
                d1 = dnms[0]!!
                d2 = dnms[1]!!
                d3 = dnms[2]!!
                d4 = dnms[3]!!
                d5 = dnms[4]!!
                d6 = dnms[5]!!
                d7 = dnms[6]!!
                segCount = scount
            }
        }

        println("\nAdded '${nm}' to the Experiment table.")
    }
    // ======================================================================
    // Create a Day
    override fun d() {
        val nm = inputName(ConceptType.DAY, ConceptState.ABSENT)                                // name
        val sk = inputSkill()
        val desc = inputString("DESCRIPTION")                       // description

        println("Input all Foundation names (X for null):")
        val fnms = inputConceptNames(ConceptType.FOUNDATION, 10)  // Foundation names

        println("\nInput all Progression names (X for null):")
        val pnms = inputConceptNames(ConceptType.PROGRESSION, 5)  // Progression names

        transaction {
            DAY.Companion.new {
                name = nm
                skillName = sk
                description = desc
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
            }
        }
        println("\nAdded '${nm}' to the Day table.")
    }
    // ======================================================================
    override fun p(datapath: String) {
        // TODO("Not yet implemented")
        val nameIn = inputName(ConceptType.PROGRESSION, ConceptState.ABSENT)
        val skillIn = inputSkill()
        val descriptionIn = inputDescription()

        println("Input challenge names in increasing order of difficulty (X for null):")
        // val challengeNames = inputEntityNames("CHALLENGE")

        ///*
        val c0In = inputChallenge(0)
        val c1In = inputChallenge(1)
        val c2In = inputChallenge(2)
        val c3In = inputChallenge(3)
        val c4In = inputChallenge(4)
        val c5In = inputChallenge(5)
        val c6In = inputChallenge(6)
        val c7In = inputChallenge(7)
        val c8In = inputChallenge(8)
        val c9In = inputChallenge(9)
        //*/

        transaction {
            PROGRESSION.Companion.new {
                name = nameIn
                skillName = skillIn
                description = descriptionIn
                c0 = c0In
                c1 = c1In
                c2 = c2In
                c3 = c3In
                c4 = c4In
                c5 = c5In
                c6 = c6In
                c7 = c7In
                c8 = c8In
                c9 = c9In
            }
        }

    }
    // ======================================================================
}