package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.database.ExamE
import com.idris.database.ExamsT
import com.idris.database.FoundationE
import com.idris.database.FoundationsT
import com.idris.database.ProgressionE
import com.idris.database.ProgressionsT
import com.idris.elo.EloTool
import com.idris.model.Skill
import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import com.idris.model.objective.Foundation
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.util.Scanner

// Call the Idris 'modify' operation for Foundation, Challenge, or Exam


object ModifyHelper : Helper() {
    // ======================================================================
    override fun f() {
        val name = inputName(ConceptType.FOUNDATION, ConceptState.PRESENT)

        println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")

        val nameNew = inputName(ConceptType.FOUNDATION, ConceptState.ABSENT)
        val skillNew = inputSkill()
        val descriptionNew = inputDescription()
        val minutesNew = inputMinutes()

        transaction {
            FoundationE.Companion.findSingleByAndUpdate(FoundationsT.name eq name) {
                if (nameNew != "-1") it.name = nameNew
                if (skillNew != "-1") it.skillName = skillNew
                if (descriptionNew != "-1") it.description = descriptionNew
                if (minutesNew != -1.0) it.minutes = minutesNew.toBigDecimal()
            }
        }
    }
    // ======================================================================
    override fun c(datapath: String) {
        transaction {
            val name = inputName(ConceptType.CHALLENGE, ConceptState.PRESENT)

            println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")

            val nameNew = inputName(ConceptType.CHALLENGE, ConceptState.ABSENT)
            val progressionNameNew = inputProgression()
            val skillNew = inputSkill()
            val descriptionNew = inputDescription()
            val minutesNew = inputMinutes()
            val oddsNew = inputOdds()
            val et = EloTool()
            val cEloNew = et.opponentRating(1500.00, oddsNew)

            transaction {
                ChallengeE.Companion.findSingleByAndUpdate(ChallengesT.name eq name) {
                    if (nameNew != "-1") it.name = nameNew
                    if (skillNew != "-1") it.skillName = skillNew
                    if (descriptionNew != "-1") it.description = descriptionNew
                    if (minutesNew != -1.0) it.minutes = minutesNew.toBigDecimal()
                    if (oddsNew != -1.0) {
                        it.cElo = cEloNew.toBigDecimal()
                        it.uElo = BigDecimal("1500.00")
                        it.uOdds = oddsNew.toBigDecimal()
                        it.attempts = 0
                        it.wins = 0
                    }
                    if (progressionNameNew != "-1") it.progressionName = progressionNameNew
                }
            }
            println("\nModified '$name' in the Challenge table.")
        }
    }
    // ======================================================================
    override fun e() {
        transaction {
            val name = inputName(ConceptType.EXAM, ConceptState.PRESENT)

            println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")

            val nameNew = inputName(ConceptType.EXAM, ConceptState.ABSENT)
            val skillNew = inputSkill()
            val descriptionNew = inputDescription()
            val minutesNew = inputMinutes()

            transaction {
                ExamE.Companion.findSingleByAndUpdate(ExamsT.name eq name) {
                    if (nameNew != "-1") it.name = nameNew
                    if (skillNew != "-1") it.skillName = skillNew
                    if (descriptionNew != "-1") it.description = descriptionNew
                    if (minutesNew != -1.0) it.minutes = minutesNew.toBigDecimal()
                }
            }
        }
    }
    // ======================================================================
    override fun x() {
        TODO("Not yet implemented")
    }
    // ======================================================================
    override fun d() {
        TODO("Not yet implemented")
    }
    // ======================================================================
    override fun p(datapath: String) {
        //TODO("Not yet implemented")
        val name = inputName(ConceptType.PROGRESSION, ConceptState.PRESENT)

        println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")
        val nameNew = inputName(ConceptType.PROGRESSION, ConceptState.ABSENT)
        val descriptionNew = inputDescription()

        val c0New = inputChallenge(0)
        val c1New = inputChallenge(1)
        val c2New = inputChallenge(2)
        val c3New = inputChallenge(3)
        val c4New = inputChallenge(4)
        val c5New = inputChallenge(5)
        val c6New = inputChallenge(6)
        val c7New = inputChallenge(7)
        val c8New = inputChallenge(8)
        val c9New = inputChallenge(9)

        transaction {
            ProgressionE.Companion.findSingleByAndUpdate(ProgressionsT.name eq name) {
                if (nameNew != "-1") it.name = nameNew
                if (descriptionNew != "-1") it.description = descriptionNew

                if (c0New != "-1") it.c0 = c0New else return@findSingleByAndUpdate
                if (c1New != "-1") it.c1 = c1New else return@findSingleByAndUpdate
                if (c2New != "-1") it.c2 = c2New else return@findSingleByAndUpdate
                if (c3New != "-1") it.c3 = c3New else return@findSingleByAndUpdate
                if (c4New != "-1") it.c4 = c4New else return@findSingleByAndUpdate
                if (c5New != "-1") it.c5 = c5New else return@findSingleByAndUpdate
                if (c6New != "-1") it.c6 = c6New else return@findSingleByAndUpdate
                if (c7New != "-1") it.c7 = c7New else return@findSingleByAndUpdate
                if (c8New != "-1") it.c8 = c8New else return@findSingleByAndUpdate
                if (c9New != "-1") it.c9 = c9New else return@findSingleByAndUpdate
            }
        }
        println("\nModified '$name' in the Progression table.")
    }
    // ======================================================================
    private fun inputOdds() : Double {
            val s = Scanner(System.`in`)
            print("ODDS  ")
            return s.nextDouble()
    }
}