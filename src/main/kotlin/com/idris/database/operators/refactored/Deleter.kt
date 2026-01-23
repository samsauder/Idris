package com.idris.database.operators.refactored

import com.idris.database.operators.todo.Operator
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object Deleter : Operator() {
    fun delete(ct: ConceptType) {  // provisionally complete
        transaction {
            val name = inputName(ct, ConceptState.PRESENT)
            val conceptE = getConceptEntity(ct, name)
            conceptE?.delete()
            println("\nDeleted '${name}' from the $ct table.")
        }
    }

    // ======================================================================
    override fun f() {
        delete(ConceptType.FOUNDATION)
    }
    // ======================================================================
    override fun c(datapath: String) {
        delete(ConceptType.CHALLENGE)
    }
    // ======================================================================
    override fun e() {
        delete(ConceptType.EXAM)
    }
    // ======================================================================
    override fun x() {
        delete(ConceptType.EXPERIMENT)
    }
    // ======================================================================
    override fun d() {
        delete(ConceptType.DAY)
    }
    // ======================================================================
    override fun p(datapath: String) {
        delete(ConceptType.PROGRESSION)
    }
    // ======================================================================
}