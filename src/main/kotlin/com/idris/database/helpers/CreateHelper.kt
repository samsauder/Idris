package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.DayE
import com.idris.database.ExamE
import com.idris.database.FoundationE
import com.idris.database.ProgressionE
import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import com.idris.model.objective.Exam
import com.idris.model.objective.Foundation
import com.idris.model.objective.Challenge
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Scanner

// Call the Idris 'create' operation for Foundation, Challenge, or Exam


object CreateHelper : Helper() {
    // ======================================================================
    override fun f() {  // add a FoundationE to the database
        val f = Foundation(
            "",
            null,
            "",
            -1.0
        )
        this.fillObjectiveCore(f, ConceptType.FOUNDATION)
        val skillName = f.skill?.id

        transaction {
            FoundationE.Companion.new {
                name = f.name
                if (skillName != null) skill = skillName;
                description = f.description
                minutes = f.minutes.toBigDecimal()
            }
        }

        println("\nAdded '${f.name}' to the Foundation table.")
    }
    // ======================================================================
    override fun c(datapath: String) {  // add a ChallengeE to the database
        val c = Challenge("", null, "", -1.0, 0.01)
        this.fillObjectiveCore(c, ConceptType.CHALLENGE)
        val skillName = c.skill?.id

        val progressionNameIn = inputProgression()

        val s = Scanner(System.`in`)
        print("ODDS  ")
        c.userOdds = s.nextDouble()
        c.generateChallengeElo()

        transaction {
            ChallengeE.Companion.new {
                name = c.name
                // skill = (c.skill?.id ?: String) as String
                if (skillName != null) skill = skillName;
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
            null,
            "",
            -1.0,
            false
        )
        this.fillObjectiveCore(e, ConceptType.EXAM)
        val skillName = e.skill?.id

        transaction {
            ExamE.Companion.new {
                name = e.name
                if (skillName != null) skill = skillName;
                description = e.description

                minutes = e.minutes.toBigDecimal()
                passed = false
            }
        }

        println("\nAdded '${e.name}' to the Exam table.")
    }
    // ======================================================================
    override fun x() {
        TODO("Not yet implemented")
    }
    // ======================================================================
    // Create a Day
    override fun d() {
        val nm = inputName(ConceptType.DAY, ConceptState.ABSENT)                                // name
        val desc = inputString("DESCRIPTION")                       // description
        val fnms = inputConceptNames(ConceptType.FOUNDATION, 10)  // Foundation names
        val pnms = inputConceptNames(ConceptType.PROGRESSION, 5)  // Progression names

        transaction {
            DayE.Companion.new {
                name = nm
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
            ProgressionE.Companion.new {
                name = nameIn
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