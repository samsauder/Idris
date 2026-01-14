package com.idris.database.helpers

import com.idris.database.ChallengeE
import com.idris.database.ChallengesT
import com.idris.database.ExamE
import com.idris.database.ExamsT
import com.idris.database.FoundationE
import com.idris.database.FoundationsT
import com.idris.database.ProgressionE
import com.idris.database.ProgressionsT
import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// Call the Idris 'delete' operation for Foundation, Challenge, or Exam


object DeleteHelper : Helper() {
    // ======================================================================
    override fun f() {
        transaction {
            val name = inputName(ConceptType.FOUNDATION, ConceptState.PRESENT)
            val fIterator =  FoundationE.find { FoundationsT.name eq name }.iterator()
            val f = fIterator.next()
            f.delete()
            println("\nDeleted '${f.name}' from the Foundation table.")
        }
    }
    // ======================================================================
    override fun c(datapath: String) {
        transaction {
            val name = inputName(ConceptType.CHALLENGE, ConceptState.PRESENT)
            val cIterator =  ChallengeE.find { ChallengesT.name eq name }.iterator()
            val c = cIterator.next()
            c.delete()
            println("\nDeleted '${c.name}' from the Challenge table.")
        }
    }
    // ======================================================================
    override fun e() {
        transaction {
            val name = inputName(ConceptType.EXAM, ConceptState.PRESENT)
            val eIterator =  ExamE.find { ExamsT.name eq name }.iterator()
            val e = eIterator.next()
            e.delete()
            println("\nDeleted '${e.name}' from the Exam table.")
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
        transaction {
            val name = inputName(ConceptType.PROGRESSION, ConceptState.PRESENT)
            val pIterator =  ProgressionE.find { ProgressionsT.name eq name }.iterator()
            val p = pIterator.next()
            p.delete()
            println("\nDeleted '${p.name}' from the Progression table.")
        }
    }
    // ======================================================================

    fun delete(ct: ConceptType) {  // provisionally complete
        transaction {
            val name = inputName(ct, ConceptState.PRESENT)
            val conceptEntity: ConceptE = when (ct) {
                ConceptType.FOUNDATION -> {FoundationE.getOneNamed(name)!!}
                ConceptType.CHALLENGE -> {ChallengeE.getOneNamed(name)!!}
                // ConceptType.EXAM -> {}
                ConceptType.DAY -> {DayE.getOneNamed(name)!!}
                ConceptType.PROGRESSION -> {ProgressionE.getOneNamed(name)!!}
                ConceptType.EXPERIMENT -> {ExperimentE.getOneNamed(name)!!}
                else -> {null}
            } as ConceptE
            conceptEntity.delete()
            println("\nDeleted '${name}' from the $ct table.")
        }
    }
}