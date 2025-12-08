package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ExamE
import com.idris.database.FoundationE
import com.idris.model.Exam
import com.idris.model.Foundation
import com.idris.model.challenge.Challenge
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Scanner

object CreateHelper : Helper() {
    // ======================================================================
    override fun f() {  // add a FoundationE to the database
        val f = Foundation(
            "",
            null,
            "",
            -1.0
        )
        this.fillObjectiveCore(f)
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
    override fun c() {  // add a ChallengeE to the database
        val c = Challenge("", null, "", -1.0, 0.01)
        this.fillObjectiveCore(c)
        val skillName = c.skill?.id

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
        this.fillObjectiveCore(e)
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
}