package com.idris.database.operators.todo

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CHALLENGES
import com.idris.database.entities.PROGRESSION
import com.idris.database.entities.PROGRESSIONS
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import com.idris.system.extra.Util.inputName
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal

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
            val resultString = inputResult()
            println()

            val cIterator = CHALLENGE.Companion.find { CHALLENGES.name eq name }.iterator()
            val cE = cIterator.next()
            val c = cE.deEntify()
            val result = if (resultString == "win") 1.0 else 0.0

            if (c.progressionName == "X") {  // null placeholder
                cManual(name, result)
            } else {
                val pIterator = PROGRESSION.Companion.find { PROGRESSIONS.name eq c.progressionName }.iterator()
                val pE = pIterator.next()
                // val p = pE.deEntify(datapath)
                val p = pE.deEntify()

                p.massLog(c.name, result)
            }
        }
    }

    fun cManual(name: String, result: Double) {
        transaction {
            val cIterator = CHALLENGE.Companion.find { CHALLENGES.name eq name }.iterator()
            val cE = cIterator.next()
            val c = cE.deEntify()

            c.log(result == 1.0)

            val challengeE = CHALLENGE.Companion.findSingleByAndUpdate(CHALLENGES.name eq name) {
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
    private fun inputResult() : String {
        // val s = Scanner(System.`in`)
        print("RESULT  ")  // win/loss
        return scanner.next()
    }
}