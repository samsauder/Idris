package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.database.ExamE
import com.idris.database.ExamsT
import com.idris.database.FoundationE
import com.idris.database.FoundationsT
import com.idris.elo.EloTool
import com.idris.model.Skill
import com.idris.model.objective.Foundation
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.util.Scanner

// Call the Idris 'modify' operation for Foundation, Challenge, or Exam


object ModifyHelper : Helper() {
    // ======================================================================
    override fun f() {
        val name = inputName()

        println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")

        val nameNew = inputName()
        val skillNew = inputSkill()
        val descriptionNew = inputDescription()
        val minutesNew = inputMinutes()

        transaction {
            FoundationE.Companion.findSingleByAndUpdate(FoundationsT.name eq name) {
               if (nameNew != "-1") it.name = nameNew
                if (skillNew != "-1") it.skill = skillNew
                if (descriptionNew != "-1") it.description = descriptionNew
                if (minutesNew != -1.0) it.minutes = minutesNew.toBigDecimal()
            }
        }
    }
    // ======================================================================
    override fun c() {
        transaction {
            val name = inputName()

            println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")

            val nameNew = inputName()
            val skillNew = inputSkill()
            val descriptionNew = inputDescription()
            val minutesNew = inputMinutes()
            val oddsNew = inputOdds()
            val et = EloTool()
            val cEloNew = et.opponentRating(1500.00, oddsNew)

            transaction {
                ChallengeE.Companion.findSingleByAndUpdate(ChallengesT.name eq name) {
                    if (nameNew != "-1") it.name = nameNew
                    if (skillNew != "-1") it.skill = skillNew
                    if (descriptionNew != "-1") it.description = descriptionNew
                    if (minutesNew != -1.0) it.minutes = minutesNew.toBigDecimal()
                    if (oddsNew != -1.0) {
                        it.cElo = cEloNew.toBigDecimal()
                        it.uElo = BigDecimal("1500.00")
                        it.uOdds = oddsNew.toBigDecimal()
                        it.attempts = 0
                        it.wins = 0
                    }
                }
            }
            println("\nModified '$name' in the Challenge table.")
        }
    }
    // ======================================================================
    override fun e() {
        transaction {
            val name = inputName()

            println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")

            val nameNew = inputName()
            val skillNew = inputSkill()
            val descriptionNew = inputDescription()
            val minutesNew = inputMinutes()

            transaction {
                ExamE.Companion.findSingleByAndUpdate(ExamsT.name eq name) {
                    if (nameNew != "-1") it.name = nameNew
                    if (skillNew != "-1") it.skill = skillNew
                    if (descriptionNew != "-1") it.description = descriptionNew
                    if (minutesNew != -1.0) it.minutes = minutesNew.toBigDecimal()
                }
            }
        }
    }

    override fun x() {
        TODO("Not yet implemented")
    }

    override fun p(datapath: String) {
        TODO("Not yet implemented")
    }
    // ======================================================================

    private fun inputOdds() : Double {
            val s = Scanner(System.`in`)
            print("ODDS  ")
            return s.nextDouble()
    }
}