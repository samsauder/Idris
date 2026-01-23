package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.DAY
import com.idris.database.entities.DAYS
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXAMS
import com.idris.database.entities.EXPERIMENT
import com.idris.database.entities.EXPERIMENTS
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.FOUNDATIONS
import com.idris.database.entities.PROGRESSION
import com.idris.database.entities.PROGRESSIONS
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.EloTool
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import java.util.Scanner

object Modifier : Operator() {
    /*
    // Modify a single attribute of the target concept
    fun modify(ct: ConceptType, at: AttributeType) {
        val name = inputName(ConceptType.FOUNDATION, ConceptState.PRESENT)  // get target
        val concept: CONCEPT

        transaction {
            when(ct) {
                ConceptType.FOUNDATION -> TODO()
                ConceptType.CHALLENGE -> TODO()
                ConceptType.EXAM -> TODO()
                ConceptType.PROGRESSION -> TODO()
                ConceptType.DAY -> TODO()
                ConceptType.EXPERIMENT -> TODO()
            }



            // func
        }
    }
    */

    /*
    // Modify the specified attribute
    fun assignAttribute(at: AttributeType) {
        val s = inputString("Enter your desired new value.")
        val concept: CONCEPT

        when (at) {
           concept =
        }
        transaction {
            when(at) {
                AttributeType.NAME -> {

                }
                AttributeType.SKILL_NAME -> {}
                AttributeType.DESCRIPTION -> {}
            }
        }
    }*/


    // ======================================================================
    override fun f() {
        val name = inputName(ConceptType.FOUNDATION, ConceptState.PRESENT)

        println("\nFor each following attribute, enter a new value or -1 to keep the existing value.")

        val nameNew = inputName(ConceptType.FOUNDATION, ConceptState.ABSENT)
        val skillNew = inputSkill()
        val descriptionNew = inputDescription()
        val minutesNew = inputMinutes()

        transaction {
            FOUNDATION.Companion.findSingleByAndUpdate(FOUNDATIONS.name eq name) {
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
                CHALLENGE.Companion.findSingleByAndUpdate(CHALLENGES.name eq name) {
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
                EXAM.Companion.findSingleByAndUpdate(EXAMS.name eq name) {
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
        val name = inputName(ConceptType.EXPERIMENT, ConceptState.PRESENT)

        println("\nFor each following attribute, enter a new value or nothing to keep the existing value (_ for rest)")
        val nameNew = inputName(ConceptType.EXPERIMENT, ConceptState.ABSENT)
        val skillNew = inputString("SKILL-NAME  ")
        val descriptionNew = inputString("DESCRIPTION  ")

        val dNames = arrayOfNulls<String>(7)
        for (i in 0..6) {  // day names
            val d = inputString("DAY  ")
            if (d == "") break
            dNames[i] = d
        }

        val segCountNew = inputInteger("# OF SEGMENTS  ")

        transaction {
            EXPERIMENT.Companion.findSingleByAndUpdate(EXPERIMENTS.name eq name) {
                if (nameNew != "") it.name = nameNew
                if (skillNew != "") it.skillName = skillNew
                if (descriptionNew != "") it.description = descriptionNew

                if (dNames[0] != null) it.d1 = dNames[0]!!
                if (dNames[1] != null) it.d2 = dNames[1]!!
                if (dNames[2] != null) it.d3 = dNames[2]!!
                if (dNames[3] != null) it.d4 = dNames[3]!!
                if (dNames[4] != null) it.d5 = dNames[4]!!
                if (dNames[5] != null) it.d6 = dNames[5]!!
                if (dNames[6] != null) it.d7 = dNames[6]!!

                if (segCountNew != null) it.segCount = segCountNew
            }
        }
    }
    // ======================================================================
    override fun d() {
        val name = inputName(ConceptType.DAY, ConceptState.PRESENT)

        println("\nFor each following attribute, enter a new value or nothing to keep the existing value.")
        val nameNew = inputName(ConceptType.DAY, ConceptState.ABSENT)
        val skillNew = inputString("SKILL-NAME  ")
        val descriptionNew = inputString("DESCRIPTION  ")

        val fNames = arrayOfNulls<String>(10)  // foundation names
        for (i in 0..9) {
            val f = inputString("FOUNDATION  ")
            if (f == "") break
            fNames[i] = f
        }

        val pNames = arrayOfNulls<String>(10)  // foundation names
        for (i in 0..4) {
            val p = inputString("PROGRESSION ")
            if (p == "") break
            pNames[i] = p
        }

        transaction {
            DAY.Companion.findSingleByAndUpdate(DAYS.name eq name) {
                if (nameNew != "") it.name = nameNew
                if (skillNew != "") it.skillName = skillNew
                if (descriptionNew != "") it.description = descriptionNew

                if (fNames[0] != null) it.f0 = fNames[0]!!
                if (fNames[1] != null) it.f1 = fNames[1]!!
                if (fNames[2] != null) it.f2 = fNames[2]!!
                if (fNames[3] != null) it.f3 = fNames[3]!!
                if (fNames[4] != null) it.f4 = fNames[4]!!
                if (fNames[5] != null) it.f5 = fNames[5]!!
                if (fNames[6] != null) it.f6 = fNames[6]!!
                if (fNames[7] != null) it.f7 = fNames[7]!!
                if (fNames[8] != null) it.f8 = fNames[8]!!
                if (fNames[9] != null) it.f9 = fNames[9]!!

                if (pNames[0] != null) it.p0 = pNames[0]!!
                if (pNames[1] != null) it.p1 = pNames[1]!!
                if (pNames[2] != null) it.p2 = pNames[2]!!
                if (pNames[3] != null) it.p3 = pNames[3]!!
                if (pNames[4] != null) it.p4 = pNames[4]!!
            }
        }

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
            PROGRESSION.Companion.findSingleByAndUpdate(PROGRESSIONS.name eq name) {
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