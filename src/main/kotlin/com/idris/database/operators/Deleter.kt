package com.idris.database.operators

import com.idris.model.enums.ConceptState
import com.idris.model.enums.ConceptType
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


// Call the Idris 'delete' operation for Foundation, Challenge, or Exam

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