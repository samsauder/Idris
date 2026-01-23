package com.idris.database.entities

import com.idris.system.concepts.Concept
import com.idris.system.extra.ConceptType
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable

// Abstract class defining common attributes for all Idris database entities


abstract class CONCEPT(id: EntityID<Int>) : IntEntity(id) {
    companion object {
        // Return the companion object associated with the given concept type
        fun companion(t: ConceptType): IntEntityClass<CONCEPT> {
            return when (t) {
                ConceptType.FOUNDATION -> { FOUNDATION.Companion }
                ConceptType.CHALLENGE -> { CHALLENGE.Companion }
                ConceptType.EXAM -> { EXAM.Companion }
                ConceptType.DAY -> { DAY.Companion }
                ConceptType.PROGRESSION -> { PROGRESSION.Companion }
                ConceptType.EXPERIMENT -> { EXPERIMENT.Companion }
                // ConceptType.RECORD -> { RECORD.Companion }
            }
        }

        // Get all entities of a given concept type
        fun getAll(t: ConceptType): SizedIterable<CONCEPT> {
            return CONCEPT.companion(t).all()
        }

        fun conceptNamed(t: ConceptType, name: String): CONCEPT {
            return when (t) {
                ConceptType.FOUNDATION -> { FOUNDATION.getOneNamed(name)!! }
                ConceptType.CHALLENGE -> { CHALLENGE.getOneNamed(name)!! }
                ConceptType.EXAM -> { EXAM.getOneNamed(name)!! }
                ConceptType.DAY -> { DAY.getOneNamed(name)!! }
                ConceptType.PROGRESSION -> { PROGRESSION.getOneNamed(name)!! }
                ConceptType.EXPERIMENT -> { EXPERIMENT.getOneNamed(name)!! }
                // ConceptType.RECORD -> { RECORD.getOneNamed(name)!! }
            }
        }
    }

    abstract fun deEntify() : Concept;  // return a Concept
}