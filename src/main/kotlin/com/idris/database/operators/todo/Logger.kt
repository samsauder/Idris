package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXAMS
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.FOUNDATIONS
import com.idris.database.entities.PROGRESSION
import com.idris.database.entities.PROGRESSIONS
import com.idris.system.concepts.Challenge
import com.idris.system.concepts.Exam
import com.idris.system.concepts.Foundation
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Util.inputName
import com.idris.system.extra.Util.inputString
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object Logger : Operator() {
    // ======================================================================
    override fun f() {
        transaction {
            TODO("Not yet implemented")
        }
    }
    // ======================================================================
    override fun c(datapath: String) {
        transaction {
            val name = inputName(ConceptType.CHALLENGE, ConceptState.PRESENT)
            val resultString = inputString("RESULT  ")
            println()

            /*
            // val cIterator = CHALLENGE.Companion.find { CHALLENGES.name eq name }.iterator()
            // val cE = cIterator.next()
            // val c = cE.deEntify()
            */

            val c = CHALLENGE.getOneNamed(name)?.deEntify()
            val result = if (resultString == "win") 1.0 else 0.0

            if (c?.progressionName == "X") {  // null placeholder
                // cManual(name, result)

                updateChallenge(c, result)
            } else {
                /*
                // val pIterator = PROGRESSION.Companion.find { PROGRESSIONS.name eq c!!.progressionName }.iterator()
                // val pE = pIterator.next()
                // val p = pE.deEntify(datapath)
                // val p = pE.deEntify()
                */

                val p = PROGRESSION.getOneNamed(c!!.progressionName)?.deEntify()
                p?.massLog(c.name, result)
            }
        }
    }

    /*
    @OptIn(ExperimentalTime::class)
    fun cManual(name: String, result: Double) {
        transaction {
            // val cIterator = CHALLENGE.find { CHALLENGES.name eq name }.iterator()
            // val cE = cIterator.next()
            // val c = cE.deEntify()

            val c: Challenge = CHALLENGE.getOneNamed(name)!!.deEntify()

            /*
            c.update(result == 1.0)

            val challengeE = CHALLENGE.findSingleByAndUpdate(CHALLENGES.name eq name) {
                it.cElo = BigDecimal.valueOf(c.challengeElo)
                it.uElo = BigDecimal.valueOf(c.userElo)
                it.uOdds = BigDecimal.valueOf(c.userOdds)
                it.attempts++
                it.wins += result.toInt()
                it.progressionName = c.progressionName
            }*/

            updateChallenge(c, result)  // update the challenge in the database according to the result of the attempt

            /*
            // add record to the record table
            val won = result == 1.0
            val challenge: Challenge = CHALLENGE.getOneNamed(name)!!.deEntify()
            val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            Creator.r("${name}___${datetime}", challenge.skillName, "", name, won, datetime)
            */
        }
    }
    */

    // Update the challenge in the database given the result on the given challenge (1.0 or 0.0)
    @OptIn(ExperimentalTime::class)
    fun updateChallenge(c: Challenge, result: Double) {
        c.update(result == 1.0)

        transaction {
            CHALLENGE.findSingleByAndUpdate(CHALLENGES.name eq c.name) {
                it.cElo = BigDecimal.valueOf(c.challengeElo)
                it.uElo = BigDecimal.valueOf(c.userElo)
                it.uOdds = BigDecimal.valueOf(c.userOdds)
                it.attempts++
                it.wins += result.toInt()
                // it.progressionName = c.progressionName

                // Creator.r("${obName}___${datetime}", c.skillName, "", obName, result == 1.0, datetime)
                val datetime = datetimeNow()
                Creator.r("${it.name}___${datetime}", it.skillName, "", it.name, result == 1.0, datetime)
            }
        }
    }

    fun updateExam(e: Exam, result: Double) {
        val passed = result == 1.0
        e.update(passed)

        EXAM.findSingleByAndUpdate(EXAMS.name eq e.name) {
            it.passed = passed
            val datetime = datetimeNow()
            Creator.r("${it.name}___${datetime}", it.skillName, "", it.name, result == 1.0, datetime)
        }

    }

    fun updateFoundation(f: Foundation) {
        f.update(true)

        FOUNDATION.findSingleByAndUpdate(FOUNDATIONS.name eq f.name) {
            val datetime = datetimeNow()
            Creator.r("${it.name}___${datetime}", it.skillName, "", it.name, true, datetime)
        }
    }

    // Return the current datetime
    @OptIn(ExperimentalTime::class)
    private fun datetimeNow(): String {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    }

    /*
    // Add a record to the database documenting: objective name, result, date
    // - return true if an objective with that name exists in the database
    // - return false otherwise
    @OptIn(ExperimentalTime::class)
    private fun addRecord(obName: String, result: Double): Boolean {
        // add record to the record table
        val C = CHALLENGE.getOneNamed(obName) ?: return false
        val c: Challenge = C.deEntify()
        val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        Creator.r("${obName}___${datetime}", c.skillName, "", obName, result == 1.0, datetime)
    }*/
    // ======================================================================
    override fun e() {
        transaction {
            TODO("Not yet implemented")
        }
    }

    override fun x() {
        TODO("Not yet implemented")
    }

    override fun d() {
        TODO("Not yet implemented")
    }

    override fun p(datapath: String) {
        TODO("Not yet implemented")
    }

    // ======================================================================
}