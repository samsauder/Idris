package com.idris.database.helpers

import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


// Call the Idris 'view' operation on an Idris entity
// * view brings up a detailed overview of the specified entity

object ViewHelper : Helper() {
    fun view(ct: ConceptType) {
        val name = inputName(ct, ConceptState.PRESENT)
        val conceptE = getConceptEntity(ct, name)
        val concept = conceptE?.deEntify()
        concept?.print()  // comprehensive view
    }

    // ======================================================================
    override fun f() {
        TODO("Not yet implemented")
        // view(ConceptType.FOUNDATION)
    }
    // ======================================================================
    override fun c(datapath: String) {
        TODO("Not yet implemented")
        // view(ConceptType.CHALLENGE)
    }
    // ======================================================================
    override fun e() {
        TODO("Not yet implemented")
        // view(ConceptType.EXAM)
    }
    // ======================================================================
    override fun x() {
        view(ConceptType.EXPERIMENT)
    }
    // ======================================================================
    override fun d() {
        view(ConceptType.DAY)
    }
    // ======================================================================
    override fun p(datapath: String) {
        view(ConceptType.PROGRESSION)
    }

}