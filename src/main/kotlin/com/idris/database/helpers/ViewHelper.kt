package com.idris.database.helpers

import com.idris.model.auxiliary.ConceptState
import com.idris.model.auxiliary.ConceptType
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


// Call the Idris 'view' operation on an Idris entity
// * view brings up a detailed overview of the specified entity

object ViewHelper : Helper() {
    override fun f() {
        view(ConceptType.FOUNDATION)
    }

    override fun c(datapath: String) {
        view(ConceptType.CHALLENGE)
    }

    override fun e() {
        TODO("Not yet implemented")
        // view(ConceptType.EXAM)
    }

    override fun x() {
        // TODO("Not yet implemented")
        view(ConceptType.EXPERIMENT)
    }

    // Print an overview of a specified Day
    override fun d() {
        /*
        val name = inputName(ConceptType.DAY, ConceptState.PRESENT)  // get a valid Day name from stdin

        transaction {
            val dayEntity: DayE = DayE.getOneNamed(name)!!
            val day: NewDay = dayEntity.deEntify()
            day.print()
        }*/
        view(ConceptType.DAY)
    }

    override fun p(datapath: String) {
        view(ConceptType.PROGRESSION)
    }

    fun view(ct: ConceptType) {
        /*
        transaction {
            /*
            val name = inputName(ct, ConceptState.PRESENT)
            val conceptEntity: ConceptE = when (ct) {
                ConceptType.FOUNDATION -> {FoundationE.getOneNamed(name)!!}
                ConceptType.CHALLENGE -> {ChallengeE.getOneNamed(name)!!}
                // ConceptType.EXAM -> {}
                ConceptType.DAY -> {DayE.getOneNamed(name)!!}
                ConceptType.PROGRESSION -> {ProgressionE.getOneNamed(name)!!}
                ConceptType.EXPERIMENT -> {ExperimentE.getOneNamed(name)!!}
                else -> {null}
            } as ConceptE*/
            val concept = getConceptOfType(ct)
            concept.print()  // comprehensive view
        }*/
        val name = inputName(ct, ConceptState.PRESENT)
        val conceptE = getConceptEntity(ct, name)
        val concept = conceptE?.deEntify()
        concept?.print()  // comprehensive view
    }

}