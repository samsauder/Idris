package com.idris.database.operators.refactored

import com.idris.database.operators.todo.Operator
import com.idris.system.extra.ConceptState
import com.idris.system.extra.ConceptType

object Viewer : Operator() {
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