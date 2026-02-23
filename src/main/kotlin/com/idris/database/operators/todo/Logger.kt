package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXAMS
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.FOUNDATIONS
import com.idris.database.entities.PROGRESSION
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

            val c = CHALLENGE.getOneNamed(name)?.deEntify()
            val result = if (resultString == "win") 1.0 else 0.0

            if (c?.progressionName == "X") {  // null placeholder
                updateChallenge(c, result)
            } else {
                val p = PROGRESSION.getOneNamed(c!!.progressionName)?.deEntify()
                p?.massLog(c.name, result)
            }
        }
    }


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