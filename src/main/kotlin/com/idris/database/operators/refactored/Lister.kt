package com.idris.database.operators.refactored

import com.idris.database.entities.CONCEPT
import com.idris.database.operators.todo.Operator
import com.idris.system.extra.ConceptType
import org.jetbrains.exposed.v1.jdbc.SizedIterable
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object Lister : Operator() {
    // List all concepts of the given concept type (with a certain width table)
    fun list(ct: ConceptType, width: Int) {
        transaction {
            println("${ct}S")
            println(bar("=", width))
            for (E in CONCEPT.getAll(ct)) {  // for each entity of type ct
                val e = E.deEntify()
                e.printL()
            }
        }
    }



    // ======================================================================
    override fun f() {
        list(ConceptType.FOUNDATION, 100)
    }
    // ======================================================================
    override fun c(datapath: String) {
        list(ConceptType.CHALLENGE, 100)
    }
    // ======================================================================
    override fun e() {
        list(ConceptType.EXAM, 100)
    }
    // ======================================================================
    override fun x() {
        list(ConceptType.EXPERIMENT, 100)
    }
    // ======================================================================
    override fun d() {
        list(ConceptType.DAY, 100)
    }
    // ======================================================================
    override fun p(datapath: String) {
        list(ConceptType.PROGRESSION, 100)
    }
    // ======================================================================
}