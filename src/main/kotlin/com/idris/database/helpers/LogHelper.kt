package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.database.ProgressionE
import com.idris.database.ProgressionsT
import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal

// Call the 'Idris' log operation for Foundation, Challenge, or Exam


object LogHelper : Helper() {
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
            val resultString = inputResult()
            println()

            val cIterator = ChallengeE.Companion.find { ChallengesT.name eq name }.iterator()
            val cE = cIterator.next()
            val c = cE.deEntify()
            val result = if (resultString == "win") 1.0 else 0.0

            if (c.progressionName == "X") {  // null placeholder
                cManual(name, result)
            } else {
                val pIterator = ProgressionE.Companion.find { ProgressionsT.name eq c.progressionName }.iterator()
                val pE = pIterator.next()
                // val p = pE.deEntify(datapath)
                val p = pE.deEntify()

                p.massLog(c.name, result)
            }
        }
    }

    fun cManual(name: String, result: Double) {
        transaction {
            val cIterator = ChallengeE.Companion.find { ChallengesT.name eq name }.iterator()
            val cE = cIterator.next()
            val c = cE.deEntify()

            c.log(result)

            val challengeE = ChallengeE.Companion.findSingleByAndUpdate(ChallengesT.name eq name) {
                it.cElo = BigDecimal.valueOf(c.challengeElo)
                it.uElo = BigDecimal.valueOf(c.userElo)
                it.uOdds = BigDecimal.valueOf(c.userOdds)
                it.attempts++
                it.wins += result.toInt()
                it.progressionName = c.progressionName
            }
        }
    }
    // ======================================================================
    override fun e() {
        transaction {
            TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }
    // ======================================================================
    private fun inputResult() : String {
        // val s = Scanner(System.`in`)
        print("RESULT  ")  // win/loss
        return scanner.next()
    }
}