package com.idris.database.operators

import com.idris.database.entities.CHALLENGE
import com.idris.database.entities.CONCEPT
import com.idris.database.entities.DAY
import com.idris.database.entities.EXAM
import com.idris.database.entities.EXPERIMENT
import com.idris.database.entities.FOUNDATION
import com.idris.database.entities.PROGRESSION
import com.idris.system.extra.ConceptType
import org.jetbrains.exposed.v1.jdbc.SizedIterable
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// Call the Idris 'list' operation for Foundation, Challenge, or Exam


object Lister : Operator() {
    // List all concepts of the given concept type (with a certain width table)
    fun list(ct: ConceptType, width: Int) {
        transaction {
            println("${ct}S")
            println(bar("=",width))
            for (E in getAll(ct)!!) {  // for each entity of type ct
                val e = E.deEntify()
                e.printL()
            }
        }
    }

    // Get all entities of a given concept type
    fun getAll(ct: ConceptType): SizedIterable<CONCEPT>? {
        var i: SizedIterable<CONCEPT>? = null
        transaction {
            i = when (ct) {
                ConceptType.FOUNDATION -> {FOUNDATION.Companion.all()}
                ConceptType.CHALLENGE -> {CHALLENGE.Companion.all()}
                ConceptType.EXAM -> {EXAM.Companion.all()}
                ConceptType.DAY -> {DAY.Companion.all()}
                ConceptType.PROGRESSION -> {PROGRESSION.Companion.all()}
                ConceptType.EXPERIMENT -> {EXPERIMENT.Companion.all()}
                // ConceptType.RECORD -> {}
            }
        }
        return i
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