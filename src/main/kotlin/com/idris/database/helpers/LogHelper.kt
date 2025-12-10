package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
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
    override fun c() {
        transaction {
            val name = inputName()
            val result = inputResult()
            println()

            val cIterator = ChallengeE.Companion.find { ChallengesT.name eq name }.iterator()
            val cE = cIterator.next()
            val c = cE.deEntify()
            val resultDouble = if (result == "win") 1.0 else 0.0
            c.log(resultDouble)

            val challengeE = ChallengeE.Companion.findSingleByAndUpdate(ChallengesT.name eq name) {
                it.cElo = BigDecimal.valueOf(c.challengeElo)
                it.uElo = BigDecimal.valueOf(c.userElo)
                it.uOdds = BigDecimal.valueOf(c.userOdds)
                it.attempts++
                it.wins += resultDouble.toInt()
            }

            // add an entry to the challenge attempts table
        }
    }
    // ======================================================================
    override fun e() {
        transaction {
            TODO("Not yet implemented")
        }
    }
    // ======================================================================

    private fun inputResult() : String {
        // val s = Scanner(System.`in`)
        print("RESULT  ")  // win/loss
        return scanner.next()
    }
}